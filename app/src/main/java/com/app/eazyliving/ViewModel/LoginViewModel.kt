package com.app.eazyliving.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.User
import com.app.eazyliving.network.ApiCalls
import kotlinx.coroutines.launch
import com.app.eazyliving.network.Retrofit.apiService

class LoginViewModel : ViewModel() {
    // LiveData to observe login state in the UI
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    // Function to attempt login
    fun login(email: String, password: String) {
        val credentials = LoginCredentials(email, password)

        viewModelScope.launch {
            val apiCalls = ApiCalls( apiService)
            val result = apiCalls.login(credentials)
            if (result != null) {
                _loginState.value = LoginState.Success(result)
                println("result $result")
            } else {
                _loginState.value = LoginState.Error("Invalid credentials or network error")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}



