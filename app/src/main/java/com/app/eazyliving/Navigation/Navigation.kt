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
import com.app.eazyliving.Screens.ModesScreen
import com.app.eazyliving.Screens.Screen
import com.app.eazyliving.Screens.UserScreen
import com.app.eazyliving.Screens.externalScreen
import com.app.eazyliving.Screens.subUserScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: LoginViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, loginViewModel = viewModel)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, loginViewModel = viewModel)
        }
        composable(route = Screen.SubUserScreen.route) {
            subUserScreen(navController = navController)
        }
        composable(route = Screen.ExternalScreen.route) {
            externalScreen(navController = navController)
        }

        composable(route = Screen.ModesScreen.route) {
            ModesScreen(navController = navController)
        }

        composable(route = Screen.UserScreen.route) {
            UserScreen(navController = navController)
        }
    }
}