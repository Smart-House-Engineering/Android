package com.app.eazyliving

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.app.eazyliving.Navigation.Navigation
import com.app.eazyliving.Screens.LoginScreen
import com.app.eazyliving.ViewModel.LoginViewModel
import com.app.eazyliving.network.Retrofit
import com.app.eazyliving.ui.theme.EazyLivingTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: LoginViewModel
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
                    HomeScreen()
                    //Header()
                    //Navigation(viewModel)
                }
            }
        }
    }
}

