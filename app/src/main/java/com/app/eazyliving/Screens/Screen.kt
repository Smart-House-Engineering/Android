package com.app.eazyliving.Screens

sealed class Screen(val route: String){
    object LoginScreen: Screen("LoginScreen")
    object HomeScreen: Screen("HomeScreen")
    }