package com.app.eazyliving.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.eazyliving.ViewModel.LoginViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.eazyliving.Screens.HomeScreen
import com.app.eazyliving.Screens.LoginScreen
import com.app.eazyliving.Screens.Screen

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
            }
        }
