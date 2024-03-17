package com.app.eazyliving.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData
import com.app.eazyliving.network.ApiCalls
import com.app.eazyliving.network.Cookies.decodeJWTAndExtractData
import com.app.eazyliving.network.Retrofit
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class SharedViewModel(private val apiCalls: ApiCalls) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> = _userEmail

    private val _userRole = MutableLiveData<String?>()
    val userRole: LiveData<String?> = _userRole

    private val _navigationDestination = MutableLiveData<String?>()
    val navigationDestination: LiveData<String?> = _navigationDestination

    private val _sensors = MutableLiveData<List<SensorData>>()
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
                val fetchedSensors = apiCalls.getSensors() ?: emptyList()
                _sensors.postValue(fetchedSensors)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error fetching sensors", e)
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
