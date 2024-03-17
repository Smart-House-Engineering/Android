package com.app.eazyliving.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.SensorData
import com.app.eazyliving.network.ApiCalls
import com.app.eazyliving.network.ApiService
import kotlinx.coroutines.launch

class HomeViewModel(private val apiCalls: ApiCalls) : ViewModel() {
    private val _sensors = MutableLiveData<List<SensorData>?>()
    val sensors: LiveData<List<SensorData>?> = _sensors

    init {
        getSensors()
    }

    private fun getSensors() {
        viewModelScope.launch {
            try {
                val fetchedSensors = apiCalls.getSensors() // Use the function from ApiCalls
                _sensors.postValue(fetchedSensors) // Post value directly since we're now expecting a List<SensorData>
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching sensors", e)
                // Here you might also want to update the UI to indicate the error
            }
        }
    }
}

