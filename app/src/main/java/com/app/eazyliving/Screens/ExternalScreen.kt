package com.app.eazyliving.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.components.Header

@Composable
fun ExternalScreen(navController: NavHostController, sharedViewModel: SharedViewModel = viewModel()) {
    val sensorData by sharedViewModel.sensors.observeAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(sensorData) {
        if (sensorData.isEmpty()) {
            Toast.makeText(context, "Error fetching sensor data", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 30.dp)
    ) {
        Header()
        Text(
            text = "External Screen",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        sensorData.forEach { data ->
            Text(text = "Sensor: ${data.sensorName}, Status: ${data.switchStateInt}")
        }

        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            BottomNavigation(navController, sharedViewModel)
        }
    }
}