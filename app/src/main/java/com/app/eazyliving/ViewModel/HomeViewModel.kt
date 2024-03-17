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
                val response = repository.getSensors()
                if (response.isSuccessful) {
                    val devices = response.body()
                    val sensorDataList = devices?.let {
                        listOf(
                            SensorData("Yellow LED", it.yellowLed > 0),
                            SensorData("Fan", it.fan)

                        )
                    }
                    _sensors.value = sensorDataList ?: listOf()
                } else {
                    Log.e("HomeViewModel", "Failed to fetch sensors: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching sensors", e)

            }
        }
    }
}
