package com.app.eazyliving.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.eazyliving.ViewModel.LoginViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.eazyliving.Screens.HomeScreen
import com.app.eazyliving.Screens.LoginScreen
import com.app.eazyliving.Screens.Screen
import com.app.eazyliving.Screens.externalScreen
import com.app.eazyliving.Screens.subUserScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(loginViewModel: LoginViewModel= viewModel()) {
        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = Screen.LoginScreen.route){

            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }

            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController = navController)
            }

            composable(route = Screen.SubUserScreen.route) {
               subUserScreen(navController = navController)
            }

            composable(route = Screen.ExternalScreen.route) {
                externalScreen(navController = navController)
            }
        }
}