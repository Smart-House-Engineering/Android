package com.app.eazyliving.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun subUserScreen(navController: NavController){
    Box(){
        Text(
            text = "Welcome subUser.",
            style = MaterialTheme.typography.bodyMedium
        )

    }
}