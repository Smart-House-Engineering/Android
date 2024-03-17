package com.app.eazyliving.Screens

import BottomBar
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.eazyliving.ViewModel.LoginViewModel
import com.app.eazyliving.components.Header
import com.app.eazyliving.components.SensorCard
import com.app.eazyliving.components.UserCard
import com.app.eazyliving.components.DateCard
import com.app.eazyliving.model.SensorData
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.app.eazyliving.R
import com.app.eazyliving.ViewModel.HomeViewModel

@SuppressLint("RememberReturnType")
@Composable
fun HomeScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel(), homeViewModel: HomeViewModel = viewModel()) {
    val userEmail by loginViewModel.userEmail.observeAsState()
    val userRole by loginViewModel.userRole.observeAsState()
    val sensors by homeViewModel.sensors.observeAsState(emptyList())
//    val sensors = remember {
//        mutableStateListOf(
//            SensorData("Light", false),
//            SensorData("Fan", false),
//            // Add more sensors as needed
//        )
//    }

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
                items(sensors.size) { index ->
                    val sensor = sensors[index]
                    SensorCard(
                        sensorName = sensor.sensorName,
                        switchState = sensor.switchState,
                        sensorIcon = {
                                    when (sensor.sensorName) {
                                        "Light" -> Icon(imageVector = Icons.Filled.Lightbulb, contentDescription = "Light")
                                        "Fan" ->Image( painterResource(R.drawable.fan), contentDescription = "Fan",
                                            modifier = Modifier.size(24.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            ) { newState ->sensors[index] = sensor.copy(switchState = newState)
                                Log.d(
                                    "SensorSwitch",
                                    "Sensor ${sensor.sensorName} state changed to $newState"
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp)) // Add some space between sensor cards
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

