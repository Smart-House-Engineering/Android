package com.app.eazyliving

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.wrapContentSize
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.app.eazyliving.ViewModel.LoginViewModel
import com.app.eazyliving.network.Retrofit
import com.app.eazyliving.ui.theme.EazyLivingTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: LoginViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Retrofit.initialize(this)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setContent {
            EazyLivingTheme {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    HomeScreen(viewModel)
                    //Header()
                    //Navigation(viewModel)
                }
            }
        }
    }
}

