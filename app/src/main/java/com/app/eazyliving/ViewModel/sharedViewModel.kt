package com.app.eazyliving.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.DeviceList
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData
import com.app.eazyliving.network.ApiCalls
import com.app.eazyliving.network.Cookies.decodeJWTAndExtractData
import com.app.eazyliving.network.Retrofit
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SharedViewModel(private val apiCalls: ApiCalls) : ViewModel() {
    private var currentDevices: DeviceList? = null

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> = _userEmail

    private val _userRole = MutableLiveData<String?>()
    val userRole: LiveData<String?> = _userRole

    private val _navigationDestination = MutableLiveData<String?>()
    val navigationDestination: LiveData<String?> = _navigationDestination

    private val _sensors = MutableLiveData<List<SensorData>>(emptyList())
    val sensors: LiveData<List<SensorData>> = _sensors

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = Retrofit.apiService.login(LoginCredentials(email, password))
            if (result.isSuccessful) {
                val token = result.headers()["Set-Cookie"] // Assuming token is in Set-Cookie header
                token?.let { processLoginResult(it) } ?: run {
                    _loginState.value = LoginState.Error("No token received")
                }
            } else {
                _loginState.value = LoginState.Error("Login failed with status code: ${result.code()}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processLoginResult(token: String) {
        val jwtPayload = decodeJWTAndExtractData(token)
        jwtPayload?.let {
            _userRole.value = it.role
            _userEmail.value = it.email
            _navigationDestination.value = when (it.role) {
                "OWNER" -> "HomeScreen"
                "TENANT" -> "SubUserScreen"
                "EXTERNAL" -> "ExternalScreen"
                else -> null
            }
            _loginState.value = LoginState.Success(it.role)
        } ?: run {
            _loginState.value = LoginState.Error("Failed to decode JWT or determine user role")
        }
    }

    fun resetNavigationDestination() {
        _navigationDestination.value = null
    }


   fun getSensors() {
        viewModelScope.launch {
            try {
                val fetchedSensors = apiCalls.getSensors()
                Log.d("fetchedSensors", "Fetched sensors: $fetchedSensors")
                if (fetchedSensors != null) {
                    // Log individual sensor details for clarity
                    fetchedSensors.forEach { sensor ->
                        Log.d("SharedViewModel", "Sensor: ${sensor.sensorName}, State: ${sensor.switchState}")
                    }
                    Log.d("SharedViewModel", "Posting fetched sensors to LiveData: $fetchedSensors")
                _sensors.postValue(fetchedSensors as List<SensorData>?)
                } else {
                    // If fetchedSensors is null, log this scenario
                    Log.d("SharedViewModel", "Fetched sensors is null")
                    _sensors.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error fetching sensors", e)
                _sensors.postValue(emptyList())

            }
        }
    }

    fun updateSensors(sensorName: String, newState: Boolean) {
        viewModelScope.launch {
            val updatedSensorValue = when (sensorName) {
                // Directly map sensor names to their new Boolean state.
                "fan", "lights", "RFan", "motion", "buzzer", "relay", "whiteLed", "button1", "button2" -> newState
                // Convert Boolean to Int for specific sensors.
                "yellowLed", "servo1", "servo2", "gasSensor", "photocell", "soilSensor", "steamSensor" -> if (newState) 1 else 0
                else -> null
            }

            updatedSensorValue?.let { value ->
                val requestBody = mapOf(sensorName to value)
                val finalRequestBody = mapOf("updatedDevices" to requestBody)

                try {
                    val response = Retrofit.apiService.updateSensors(finalRequestBody)
                    if (response.isSuccessful && response.body() != null) {
                        val updateResponse = response.body()!!
                        val devices = updateResponse.updatedHome.devices

                        // Map the updated devices from the response back to sensor data objects.
                        val updatedSensors = _sensors.value?.map { sensor ->
                            when (sensor.sensorName) {
                                "fan" -> sensor.copy(switchState = devices.fan)
                                "lights" -> sensor.copy(switchState = devices.lights)
                                "RFan" -> sensor.copy(switchState = devices.RFan)
                                "motion" -> sensor.copy(switchState = devices.motion)
                                "buzzer" -> sensor.copy(switchState = devices.buzzer)
                                "relay" -> sensor.copy(switchState = devices.relay)
                                "whiteLed" -> sensor.copy(switchState = devices.whiteLed)
                                "button1" -> sensor.copy(switchState = devices.button1)
                                "button2" -> sensor.copy(switchState = devices.button2)
                                "yellowLed" -> sensor.copy(switchState = devices.yellowLed > 0)
                                "servo1" -> sensor.copy(switchState = devices.servo1 > 0)
                                "servo2" -> sensor.copy(switchState = devices.servo2 > 0)
                                "gasSensor" -> sensor.copy(switchState = devices.gasSensor > 0)
                                "photocell" -> sensor.copy(switchState = devices.photocell > 0)
                                "soilSensor" -> sensor.copy(switchState = devices.soilSensor > 0)
                                "steamSensor" -> sensor.copy(switchState = devices.steamSensor > 0)
                                else -> sensor // No update if sensor name doesn't match.
                            }
                        } ?: emptyList()

                        // Post the updated sensor list.
                        _sensors.postValue(updatedSensors)
                    } else {
                        Log.e("ViewModel", "Failed to update sensor state. Error: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("ViewModel", "Network error or serialization issue when updating sensors", e)
                }
            }
        }
    }


    fun startSensorUpdates() {
        viewModelScope.launch {
            while (isActive) {  // Keeps this coroutine running as long as it's active
                try {
                    val fetchedSensors = apiCalls.getSensors()
                    if (fetchedSensors != null) {
                        _sensors.postValue(fetchedSensors!!)
                    }
                    delay(20000)  // Wait for 5 seconds before refreshing again (you can adjust this value)
                } catch (e: Exception) {
                    Log.e("SharedViewModel", "Error fetching sensors", e)
                }
            }
        }
    }



}


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val role: String) : LoginState()
    data class Error(val message: String) : LoginState()
}
