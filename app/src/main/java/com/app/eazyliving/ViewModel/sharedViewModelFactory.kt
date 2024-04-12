package com.app.eazyliving.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.eazyliving.network.ApiCalls

class SharedViewModelFactory(private val apiCalls: ApiCalls) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(apiCalls) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
