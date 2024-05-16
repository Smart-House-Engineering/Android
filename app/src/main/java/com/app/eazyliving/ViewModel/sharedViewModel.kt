package com.app.eazyliving.ViewModel

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.components.SessionRepository
import com.app.eazyliving.model.DeviceList
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData
import com.app.eazyliving.network.ApiCalls
import com.app.eazyliving.network.Cookies.decodeJWTAndExtractData
import com.app.eazyliving.network.Retrofit
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SharedViewModel(private val apiCalls: ApiCalls,private val sessionRepository: SessionRepository ) : ViewModel() {
    private var currentDevices: DeviceList? = null

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState // Represents the current state of the login process.

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> = _userEmail // Represents the email of the currently logged in user.

    private val _userRole = MutableLiveData<String?>()
    val userRole: LiveData<String?> = _userRole // Represents the role of the currently logged in user.

    private val _navigationDestination = MutableLiveData<String?>()
    val navigationDestination: LiveData<String?> = _navigationDestination // Determines the next screen to navigate to after login, based on the user's role.

    private val _sensors = MutableLiveData<List<SensorData>>(emptyList())
    val sensors: LiveData<List<SensorData>> = _sensors // Represents the current state of the sensors.

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn = _isLoggedIn.asStateFlow() // Represents the current login status of the user.

    private val _shouldUpdateSensors = MutableStateFlow(true)
    val shouldUpdateSensors: StateFlow<Boolean> = _shouldUpdateSensors.asStateFlow() // Determines whether the sensors should be updated.

    private var sensorUpdateJob: Job? = null // Job that fetches sensor data at regular intervals.

    // Attempts to log in a user with the provided email and password.
    @RequiresApi(Build.VERSION_CODES.O)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = apiCalls.login(LoginCredentials(email, password))
            if (result != null) {
                processLoginResult(result)
            } else {
                _loginState.value = LoginState.Error("Login failed")
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

    // Resets the navigation destination to null.
    fun resetNavigationDestination() {
        _navigationDestination.value = null
    }

    // Fetches the current state of the sensors.
    fun getSensors() {
        viewModelScope.launch {
            try {
                val fetchedSensors = apiCalls.getSensors()
                Log.d("fetchedSensors", "Fetched sensors: $fetchedSensors")
                if (fetchedSensors != null) {
                    // Log individual sensor details for clarity
                    fetchedSensors.forEach { sensor ->
                        Log.d(
                            "SharedViewModel",
                            "Sensor: ${sensor.sensorName}, State: ${sensor.switchState}"
                        )
                    }
                    Log.d("SharedViewModel", "Posting fetched sensors to LiveData: $fetchedSensors")
                    _sensors.postValue(fetchedSensors as List<SensorData>?)
                } else {
                    Log.d("SharedViewModel", "Fetched sensors is null")
                    _sensors.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error fetching sensors", e)
                _sensors.postValue(emptyList())

            }
        }
    }

    // Updates the state of a sensor with the provided name to the new state.
    fun updateSensors(sensorName: String, newState: Boolean) {
        viewModelScope.launch {
            val updatedSensorValue = when (sensorName) {
                // Directly map sensor names to their new Boolean state.
                "fan", "lights", "RFan", "motion", "buzzer", "relay", "whiteLed", "button1", "button2" -> newState
                // Convert Boolean to Int for specific sensors.
                "yellowLed", "door", "window", "gasSensor", "photocell", "soilSensor", "steamSensor" -> if (newState) 1 else 0
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
                                "RFan" -> sensor.copy(switchState = devices.RFan)
                                "motion" -> sensor.copy(switchState = devices.motion)
                                "buzzer" -> sensor.copy(switchState = devices.buzzer)
                                "relay" -> sensor.copy(switchState = devices.relay)
                                "whiteLed" -> sensor.copy(switchState = devices.whiteLed)
                                "button1" -> sensor.copy(switchState = devices.button1)
                                "button2" -> sensor.copy(switchState = devices.button2)
                                "yellowLed" -> sensor.copy(switchState = devices.yellowLed > 0)
                                "servo1" -> sensor.copy(switchState = devices.door > 0)
                                "servo2" -> sensor.copy(switchState = devices.window > 0)
                                "gasSensor" -> sensor.copy(switchState = devices.gasSensor > 0)
                                "photocell" -> sensor.copy(switchState = devices.photocell > 0)
                                "soilSensor" -> sensor.copy(switchState = devices.soilSensor > 0)
                                "steamSensor" -> sensor.copy(switchState = devices.steamSensor > 0)
                                else -> sensor
                            }
                        } ?: emptyList()

                        _sensors.postValue(updatedSensors)
                    } else {
                        Log.e(
                            "ViewModel",
                            "Failed to update sensor state. Error: ${
                                response.errorBody()?.string()
                            }"
                        )
                    }
                } catch (e: Exception) {
                    Log.e(
                        "ViewModel",
                        "Network error or serialization issue when updating sensors",
                        e
                    )
                }
            }
        }
    }

    // Starts a repeating job that fetches the current sensor data every 5 seconds.
    fun startSensorUpdates() {
        viewModelScope.launch {
            sensorUpdateJob = viewModelScope.launch {
                while (isActive) {
                    try {
                        val fetchedSensors = apiCalls.getSensors()
                        if (fetchedSensors != null) {
                            _sensors.postValue(fetchedSensors!!)
                        }
                        delay(5000)
                    } catch (e: Exception) {
                        Log.e("SharedViewModel", "Error fetching sensors", e)
                    }
                }
            }
        }
    }

    // Stops the repeating job that fetches the current sensor data.
    fun stopSensorUpdates() {
        sensorUpdateJob?.cancel()
        sensorUpdateJob = null

        _sensors.postValue(emptyList())
    }

    // Logs out the current user, cancels the sensor update job, and clears the sensor data.
    fun logout() {
        viewModelScope.launch {
            sensorUpdateJob?.cancel()
            sensorUpdateJob = null
            val success = sessionRepository.logout()
            if (success) {
                _isLoggedIn.value = false
                _sensors.postValue(emptyList())
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
