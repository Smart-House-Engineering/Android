package com.app.eazyliving.Screens

import BottomBar
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.eazyliving.ViewModel.LoginViewModel
import com.app.eazyliving.components.Header
import com.app.eazyliving.components.SensorCard
import com.app.eazyliving.components.UserCard
import com.app.eazyliving.components.DateCard

@Composable
fun HomeScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val userEmail by loginViewModel.userEmail.observeAsState()
    val userRole by loginViewModel.userRole.observeAsState()

    Log.d("HomeScreen", "User Email: $userEmail")
    Log.d("HomeScreen", "User Role: $userRole")
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Header()
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                UserCard(userEmail = userEmail, userRole = userRole)
                Spacer(modifier = Modifier.width(16.dp))
                DateCard()
            }
            var switchState by remember { mutableStateOf(false) }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(4) { rowIndex ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(2) { columnIndex ->
                            SensorCard(
                                switchState = switchState,
                                onSwitchStateChanged = { newState -> switchState = newState }
                            )
                        }
                    }
                }
            }
        }
        BottomBar(
            onHomeClick = { /* Handle Home click */ },
            onUserClick = { /* Handle User click */ },
            onModeClick = { /* Handle Mode click */ },
            onLogoutClick = { /* Handle Logout click */ }
        )
    }
}

