package com.app.eazyliving.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.SetModeRequest
import com.app.eazyliving.model.UpdatedModes
import com.app.eazyliving.network.ApiService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ModesViewModel(private val apiService: ApiService) : ViewModel() {
    private val _emergencyMode = MutableStateFlow(false)
    val emergencyMode = _emergencyMode.asStateFlow()
    private var emergencyModeJob: Job? = null

    fun fetchEmergencyModeStatus() {
        stopFetchingEmergencyModeStatus()
        emergencyModeJob = viewModelScope.launch {
            while (true) {
                try {
                    val response = apiService.getModes()
                    println("responseValue ${response.body()}")
                    if (response.isSuccessful) {
                        _emergencyMode.value = response.body()?.modes?.emergency ?: false
                    }
                } catch (e: Exception) {
                   println("exception: $e")
                }
                delay( 2000)
            }
        }
    }

     fun stopFetchingEmergencyModeStatus() {
        emergencyModeJob?.cancel()
        emergencyModeJob = null
    }


    fun toggleEmergencyMode() {
        viewModelScope.launch {
            try {
                val newStatus = !_emergencyMode.value
                val request = SetModeRequest(UpdatedModes(newStatus))
                val response = apiService.setModes(request)
                if (response.isSuccessful) {
                    _emergencyMode.value = newStatus
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopFetchingEmergencyModeStatus()
    }
}
