package com.app.eazyliving.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.model.DeleteUser
import com.app.eazyliving.model.UserCredentials
import com.app.eazyliving.model.UserDetails
import com.app.eazyliving.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext


sealed class UserUIState {
    object Idle : UserUIState()
    object Loading : UserUIState()
    class Success(val message: String) : UserUIState()
    class Error(val error: String) : UserUIState()
}

class UserViewModel(private val apiService: ApiService) : ViewModel() {
    private val _uiState = MutableStateFlow<UserUIState>(UserUIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _users = MutableStateFlow<List<UserDetails>>(emptyList())
    val users = _users.asStateFlow()


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

    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserUIState.Loading
            try {
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    // Assuming response.body() returns a List<User>
                    val filteredUsers = response.body()?.filter { it.role != "OWNER" } ?: emptyList()
                    _users.value = filteredUsers
                    _uiState.value = UserUIState.Success("Users fetched successfully")
                } else {
                    _uiState.value = UserUIState.Error("Failed to fetch users: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UserUIState.Error("Error fetching users: ${e.localizedMessage}")
            }
        }
    }
fun deleteUser(email: String) {
    _uiState.value = UserUIState.Loading
    viewModelScope.launch {
        try {
            val deleteUserRequest = DeleteUser(deleteUserEmail = email)
            val response = apiService.deleteUser(deleteUserRequest)
            if (response.isSuccessful) {
                _uiState.value = UserUIState.Success("User deleted successfully")
                // Refresh user list or handle success
            } else {
                _uiState.value = UserUIState.Error("Failed to delete user: ${response.message()}")
            }
        } catch (e: Exception) {
            _uiState.value = UserUIState.Error("An error occurred: ${e.localizedMessage}")
        }
    }
}

}