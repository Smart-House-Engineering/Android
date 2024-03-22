package com.app.eazyliving.Screens

import BottomBar
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.eazyliving.R
import com.app.eazyliving.ViewModel.SharedViewModel
import kotlinx.coroutines.launch


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

    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        UserInfoRow(userEmail, userRole)
        sensors?.let { SensorsGrid(it, sharedViewModel) }
        BottomNavigation()
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

                            "yellow LED" -> Image(
                                painterResource(R.drawable.yellowled),
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
fun BottomNavigation() {
        BottomBar(
            onHomeClick = { /* Handle Home click */ },
            onUserClick = { /* Handle User click */ },
            onModeClick = { /* Handle Mode click */ },
            onLogoutClick = { /* Handle Logout click */ }
        )
    }


