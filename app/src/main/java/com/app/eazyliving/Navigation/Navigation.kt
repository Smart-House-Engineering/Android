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
import com.app.eazyliving.Screens.ModesScreen
import com.app.eazyliving.Screens.Screen
import com.app.eazyliving.Screens.UserScreen
import com.app.eazyliving.Screens.ExternalScreen
import com.app.eazyliving.Screens.subUserScreen
import com.app.eazyliving.ViewModel.ModesViewModel
import com.app.eazyliving.ViewModel.ModesViewModelFactory
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.ViewModel.SharedViewModelFactory
import com.app.eazyliving.ViewModel.UserViewModel
import com.app.eazyliving.ViewModel.UserViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(sharedViewModelFactory: SharedViewModelFactory, userViewModelFactory: UserViewModelFactory, modesViewModelFactory: ModesViewModelFactory) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel(factory = sharedViewModelFactory)
    val userViewModel:UserViewModel  = viewModel(factory = userViewModelFactory)
    val modeViewModel: ModesViewModel = viewModel(factory = modesViewModelFactory)

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
            ExternalScreen(navController = navController, sharedViewModel = sharedViewModel, modesViewModel = modeViewModel)
        }

        composable(route = Screen.ModesScreen.route) {
            ModesScreen(navController = navController,sharedViewModel, modeViewModel)
        }

        composable(route = Screen.UserScreen.route) {
            UserScreen(navController = navController, userViewModel = userViewModel,sharedViewModel)
        }
    }
}