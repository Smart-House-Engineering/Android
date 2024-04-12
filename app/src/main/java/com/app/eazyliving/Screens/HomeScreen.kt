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
import androidx.compose.ui.Alignment
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
    LaunchedEffect(Unit) {
//       sharedViewModel.getSensors()
       sharedViewModel.startSensorUpdates()
    }

    Column (modifier = Modifier.fillMaxSize()){
        Header()
        UserInfoRow(userEmail, userRole)
        Box(modifier = Modifier.weight(1f)) {
            sensors?.let { SensorsGrid(it, sharedViewModel) }
        }
        BottomNavigation(navController)
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

                            "lights" -> Image(
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
fun BottomNavigation(navController: NavHostController) {
        BottomBar(
            onHomeClick = { navController.navigate("HomeScreen") },
            onUserClick = { /* Handle User click */ },
            onModeClick = { /* Handle Mode click */ },
            onLogoutClick = { navController.navigate("login")}
        )
    }


