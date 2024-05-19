package com.app.eazyliving.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.eazyliving.R
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.components.Header
import com.app.eazyliving.components.UserCard
import com.app.eazyliving.components.DateCard
import com.app.eazyliving.components.ExternalBottomBar
import com.app.eazyliving.components.ExternalSensorCard
import com.app.eazyliving.components.TenantBottomBar
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
    val filteredSensors = sensors.filterNot { sensor ->
        sensor.sensorName in listOf("button1", "button2", "motion", "photocell", "RFan")
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        if (filteredSensors.isNotEmpty()) {
            items(items = filteredSensors, key = { sensor -> sensor.sensorName }) { sensor ->
                Log.d("sensorData", sensor.toString())

                ExternalSensorCard(
                    sensorName = sensor.sensorName,
                    switchStateInt = sensor.switchStateInt,
                    switchStateBool = sensor.switchStateBool
                    /*
                    sensorIcon = {
                        when (sensor.sensorName) {
                            "yellowLed" -> Image(
                                painterResource(R.drawable.lights),
                                contentDescription = "yellowLed",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "fan" -> Image(
                                painterResource(R.drawable.fan), contentDescription = "Fan",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "buzzer" -> Image(

                                painterResource(R.drawable.buzzer), contentDescription = "Buzzer",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "relay" -> Image(
                                painterResource(R.drawable.relay), contentDescription = "relay",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "door" -> Image(
                                painterResource(R.drawable.door), contentDescription = "door",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "window" -> Image(
                                painterResource(R.drawable.window), contentDescription = "window",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "gasSensor" -> Image(
                                painterResource(R.drawable.gassensor), contentDescription = "gasSensor",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "soilSensor" -> Image(

                                painterResource(R.drawable.soilsensor), contentDescription = "soilSensor",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "steamSensor" -> Image(
                                painterResource(R.drawable.steamsensor), contentDescription = "steamSensor",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "whiteLed" -> Image(
                                painterResource(R.drawable.whiteled), contentDescription = "whiteLed",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    */
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add some space between sensor cards

            }
        }
    }
}

@Composable
fun ExternalBottomNavigation(navController: NavHostController, sharedViewModel: SharedViewModel) {
    ExternalBottomBar(
       // onHomeClick = { navController.navigate(Screen.HomeScreen.route) },
        onLogoutClick = { sharedViewModel.logout() }
    )
}