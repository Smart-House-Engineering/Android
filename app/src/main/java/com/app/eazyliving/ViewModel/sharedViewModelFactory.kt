package com.app.eazyliving.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.eazyliving.components.SessionRepository
import com.app.eazyliving.network.ApiCalls

class SharedViewModelFactory(private val apiCalls: ApiCalls, private val sessionRepository: SessionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(apiCalls, sessionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
