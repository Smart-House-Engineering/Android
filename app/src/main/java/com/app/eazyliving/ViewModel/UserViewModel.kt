package com.app.eazyliving.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.UserCredentials
import com.app.eazyliving.network.ApiService
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class UserUIState {
    object Idle : UserUIState()
    object Loading : UserUIState()
    class Success(val message: String) : UserUIState()
    class Error(val error: String) : UserUIState()
}

class UserViewModel(private val apiService: ApiService) : ViewModel() {
    private val _uiState = MutableStateFlow<UserUIState>(UserUIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun addUser(email: String, password: String, role: String) {
        _uiState.value = UserUIState.Loading
        viewModelScope.launch {
            try {
                val response = apiService.addUsers(UserCredentials(email, password, role))
                if (response.isSuccessful) {
                    _uiState.value = UserUIState.Success("User added successfully")
                } else {
                    _uiState.value = UserUIState.Error("Failed to add user: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UserUIState.Error("An error occurred: ${e.localizedMessage}")
            }
        }
    }
}