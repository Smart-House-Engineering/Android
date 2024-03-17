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

    private val _sensors = MutableLiveData<List<Devices>>()
    val sensors: LiveData<List<Devices>> = _sensors

    init {
        getSensors()
    }

    private fun getSensors() {
        viewModelScope.launch {
            try {
                val fetchedSensors = repository.getSensors()
                _sensors.value = fetchedSensors
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching sensors", e)
                // Handle errors appropriately in your app context
            }
        }
    }
}
