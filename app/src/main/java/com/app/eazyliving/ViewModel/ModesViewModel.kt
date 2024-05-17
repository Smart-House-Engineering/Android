package com.app.eazyliving.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.SetModeRequest
import com.app.eazyliving.model.UpdatedModes
import com.app.eazyliving.network.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ModesViewModel(private val apiService: ApiService) : ViewModel() {
    private val _emergencyMode = MutableStateFlow(false)
    val emergencyMode = _emergencyMode.asStateFlow()

    init {
        fetchEmergencyModeStatus()
    }

    private fun fetchEmergencyModeStatus() {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = apiService.getModes()
                    if (response.isSuccessful) {
                        _emergencyMode.value = response.body()?.modes?.emergency ?: false
                    }
                } catch (e: Exception) {
                    // Handle error
                }
                delay(5000) // Fetch every 5 seconds
            }
        }
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
}
