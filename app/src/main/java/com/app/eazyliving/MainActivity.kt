package com.app.eazyliving

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.wrapContentSize
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.eazyliving.Navigation.Navigation
import com.app.eazyliving.ViewModel.ModesViewModel
import com.app.eazyliving.ViewModel.ModesViewModelFactory
import com.app.eazyliving.ViewModel.SharedViewModelFactory
import com.app.eazyliving.ViewModel.UserViewModelFactory
import com.app.eazyliving.components.SessionRepository
import com.app.eazyliving.network.ApiCalls
import com.app.eazyliving.network.Retrofit
import com.app.eazyliving.network.Retrofit.apiService
import com.app.eazyliving.ui.theme.EazyLivingTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Retrofit.initialize(this)
        val apiCalls = ApiCalls( apiService)
        val sessionRepository = SessionRepository(this)
        val modesViewModelFactory = ModesViewModelFactory(apiService)
        val modesViewModel: ModesViewModel by viewModels { modesViewModelFactory }

        val sharedViewModelFactory = SharedViewModelFactory(apiCalls, sessionRepository, modesViewModel)
        val userViewModelFactory = UserViewModelFactory(apiService)

        setContent {
            EazyLivingTheme {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(sharedViewModelFactory,  userViewModelFactory, modesViewModelFactory )
                }
            }
        }
    }
}

