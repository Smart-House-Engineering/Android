package com.app.eazyliving.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.SensorData
import com.app.eazyliving.network.ApiService
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ApiService) : ViewModel() {

    private val _sensors = MutableLiveData<List<SensorData>>()
    val sensors: LiveData<List<SensorData>> = _sensors

    init {
        getSensors()
    }

    private fun getSensors() {
        viewModelScope.launch {
            try {
                val response = repository.getSensors()  // Ensure this matches the actual function signature
                if (response.isSuccessful) {
                    val devices = response.body()  // This should be a Devices object or List<Devices>
                    val sensorDataList = devices?.let {
                        listOf(
                            SensorData("Fan", it.fan),
                            SensorData("Yellow LED", it.yellowLed > 0)  // Assuming these properties exist
                            // Convert other device states as needed
                        )
                    }
                    _sensors.value = sensorDataList ?: listOf()  // Update LiveData with the converted list
                } else {
                    Log.e("HomeViewModel", "Failed to fetch sensors: ${response.errorBody()?.string()}")
                    // Optionally update LiveData to indicate the error state
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching sensors", e)
                // Optionally update LiveData to indicate the error state
            }
        }
    }
}
