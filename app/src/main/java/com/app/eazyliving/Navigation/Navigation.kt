package com.app.eazyliving.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.eazyliving.Screens.HomeScreen
import com.app.eazyliving.Screens.LoginScreen
import com.app.eazyliving.Screens.Screen
import com.app.eazyliving.Screens.externalScreen
import com.app.eazyliving.Screens.subUserScreen
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.ViewModel.SharedViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(sharedViewModelFactory: SharedViewModelFactory) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel(factory = sharedViewModelFactory)
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.HomeScreen.route) {

            HomeScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.SubUserScreen.route) {
            subUserScreen(navController = navController)
        }
        composable(route = Screen.ExternalScreen.route) {
            externalScreen(navController = navController)
        }
    }
}