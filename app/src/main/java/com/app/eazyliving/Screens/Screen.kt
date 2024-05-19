package com.app.eazyliving.Screens

sealed class Screen(val route: String){
    object LoginScreen: Screen("LoginScreen")
    object HomeScreen: Screen("HomeScreen")

    object SubUserScreen: Screen("SubUserScreen")

    object ExternalScreen: Screen("ExternalScreen")

    object ModesScreen: Screen("ModesScreen")

    object UserScreen: Screen("UserScreen")

    }