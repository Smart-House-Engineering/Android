package com.app.eazyliving.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.components.Header

@Composable
fun ModesScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val userRole by sharedViewModel.userRole.observeAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        Text(
            text = "Modes Screen",
            textAlign = TextAlign.Center
        )
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            when (userRole) {
                "TENANT" -> TenantBottomNavigation(navController, sharedViewModel)
                "OWNER" -> BottomNavigation(navController, sharedViewModel)
            }
        }
    }

}
