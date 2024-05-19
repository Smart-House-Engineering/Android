package com.app.eazyliving.Screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.components.Header
import com.app.eazyliving.components.UserCard
import com.app.eazyliving.components.DateCard
import com.app.eazyliving.components.ExternalBottomBar
import com.app.eazyliving.components.ExternalSensorCard
import com.app.eazyliving.model.SensorData

@Composable
fun ExternalScreen(navController: NavHostController, sharedViewModel: SharedViewModel = viewModel()) {
    val userEmail by sharedViewModel.userEmail.observeAsState()
    val userRole by sharedViewModel.userRole.observeAsState()
    val isLoggedIn by sharedViewModel.isLoggedIn.collectAsState()
    val sensors by sharedViewModel.sensors.observeAsState()

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            sharedViewModel.stopSensorUpdates()

            try {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
            } catch (e: Exception) {
                Log.e("NavigationError", "Failed to navigate: ${e.localizedMessage}")
            }
        }
        else {
            sharedViewModel.startSensorUpdates()
        }
    }

    Column (modifier = Modifier.fillMaxSize()){
        Header()
        UserInfo(userEmail, userRole)
        Box(modifier = Modifier.weight(1f)) {
            sensors?.let { ExternalSensorsGrid(it, sharedViewModel) }
        }
        when (userRole) {
            "EXTERNAL" -> ExternalBottomNavigation(navController, sharedViewModel)
        }
    }
}

@Composable
fun UserInfo(userEmail: String?, userRole: String?) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        UserCard(userEmail = userEmail, userRole = userRole)
        Spacer(modifier = Modifier.width(16.dp))
        DateCard()
    }
}

@Composable
fun ExternalSensorsGrid(sensors: List<SensorData>, sharedViewModel: SharedViewModel) {
    val gasSensor = sensors.find { it.sensorName == "gasSensor" }
    val otherSensors = sensors.filterNot { it.sensorName == "gasSensor" || it.sensorName in listOf("button1", "button2", "motion", "photocell", "RFan") }

    Column {
        gasSensor?.let {
            ExternalSensorCard(
                sensorName = it.sensorName,
                switchStateInt = it.switchStateInt,
                switchStateBool = it.switchStateBool
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(items = otherSensors, key = { sensor -> sensor.sensorName }) { sensor ->
                Log.d("sensorData", sensor.toString())

                ExternalSensorCard(
                    sensorName = sensor.sensorName,
                    switchStateInt = sensor.switchStateInt,
                    switchStateBool = sensor.switchStateBool
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add some space between sensor cards
            }
        }
    }
}

@Composable
fun ExternalBottomNavigation(navController: NavHostController, sharedViewModel: SharedViewModel) {
    ExternalBottomBar(
        onLogoutClick = { sharedViewModel.logout() }
    )
}