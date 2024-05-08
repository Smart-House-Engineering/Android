package com.app.eazyliving.Screens

import BottomBar
import android.util.Log
import androidx.compose.foundation.Image
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
import com.app.eazyliving.components.Header
import com.app.eazyliving.components.SensorCard
import com.app.eazyliving.components.UserCard
import com.app.eazyliving.components.DateCard
import com.app.eazyliving.model.SensorData
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.app.eazyliving.R
import com.app.eazyliving.ViewModel.SharedViewModel


@Composable
fun HomeScreen(navController: NavHostController,
               sharedViewModel: SharedViewModel = viewModel()) {
    val userEmail by sharedViewModel.userEmail.observeAsState()
    val userRole by sharedViewModel.userRole.observeAsState()
    val sensors by sharedViewModel.sensors.observeAsState()
    val isLoggedIn by sharedViewModel.isLoggedIn.collectAsState()
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
                sharedViewModel.stopSensorUpdates()  // Ensure updates are stopped before navigation

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
        UserInfoRow(userEmail, userRole)
        Box(modifier = Modifier.weight(1f)) {
            sensors?.let { SensorsGrid(it, sharedViewModel) }
        }

        BottomNavigation(navController,sharedViewModel)

    }

}

@Composable
fun UserInfoRow(userEmail: String?, userRole: String?) {

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
fun SensorsGrid(sensors: List<SensorData>, sharedViewModel: SharedViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        if (sensors.isNotEmpty()) {
            items(items = sensors, key = { sensor -> sensor.sensorName }) { sensor ->
                Log.d("sensorData", sensor.toString())

                SensorCard(
                    sensorName = sensor.sensorName,
                    switchState = sensor.switchState,
                    sensorIcon = {
                        when (sensor.sensorName) {

                            "y" +
                                    "Yellow Ledlight" -> Image(
                                painterResource(R.drawable.lights),
                                contentDescription = "yellowLed",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            /*"Fan" -> Image(
                                painterResource(R.drawable.fan), contentDescription = "Fan",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )*/
                            "RFan" -> Image(
                                painterResource(R.drawable.rfan), contentDescription = "RFan",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            /*"Motion" -> Image(
                                painterResource(R.drawable.motion), contentDescription = "Motion",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )*/
                            "Buzzer" -> Image(
                                painterResource(R.drawable.buzzer), contentDescription = "Buzzer",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "Relay" -> Image(
                                painterResource(R.drawable.relay), contentDescription = "relay",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "Door" -> Image(
                                painterResource(R.drawable.door), contentDescription = "door",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "Window" -> Image(
                                painterResource(R.drawable.window), contentDescription = "window",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "Gas Sensor" -> Image(
                                painterResource(R.drawable.gassensor), contentDescription = "gasSensor",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            /*"Photocell" -> Image(
                                painterResource(R.drawable.photocell), contentDescription = "Photocell",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )*/
                            "Soil Sensor" -> Image(
                                painterResource(R.drawable.soilsensor), contentDescription = "soilSensor",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "Steam Sensor" -> Image(
                                painterResource(R.drawable.steamsensor), contentDescription = "steamSensor",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "White Ledlight" -> Image(
                                painterResource(R.drawable.whiteled), contentDescription = "whiteLed",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            /*"Button1" -> Image(
                                painterResource(R.drawable.button1), contentDescription = "button1",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            "Button2" -> Image(
                                painterResource(R.drawable.button2), contentDescription = "button2",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )*/
                        }
                    }
                ) { newState ->
                    sharedViewModel.updateSensors(sensor.sensorName, newState)
                    Log.d("SensorSwitch", "Sensor ${sensor.sensorName} state changed to $newState")
                }
                Spacer(modifier = Modifier.height(8.dp)) // Add some space between sensor cards
            }
    }
        }
    }

@Composable
fun BottomNavigation(navController: NavHostController,  sharedViewModel: SharedViewModel) {
    BottomBar(
        onHomeClick = { navController.navigate(Screen.HomeScreen.route) },
        onUserClick = { navController.navigate(Screen.UserScreen.route) },
        onModeClick = { navController.navigate(Screen.ModesScreen.route) },
        onLogoutClick = { sharedViewModel.logout() }

    )
}
