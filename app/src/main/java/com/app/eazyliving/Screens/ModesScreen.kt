package com.app.eazyliving.Screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.eazyliving.R
import com.app.eazyliving.ViewModel.ModesViewModel
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.components.Header
import com.app.eazyliving.components.SensorCard

@Composable
fun ModesScreen(navController: NavHostController, sharedViewModel: SharedViewModel, modesViewModel: ModesViewModel) {
    val userRole by sharedViewModel.userRole.observeAsState()
    val viewMode by modesViewModel.emergencyMode.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
        Header()
        Text(
            text = "Modes Screen",
            textAlign = TextAlign.Center
        )
                Spacer(modifier = Modifier.height(16.dp))

                SensorCard(
                    sensorName = "Emergency Mode",
                    switchState = viewMode,
                    sensorIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.emergency),
                            contentDescription = "Emergency Icon",
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onSwitchStateChanged = {
                        modesViewModel.toggleEmergencyMode()
                    }
                )

                Text(
                    text = if (viewMode) "Emergency mode is currently ON" else "Emergency mode is currently OFF",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

        Spacer(modifier = Modifier.weight(1f))

        when (userRole) {
            "TENANT" -> TenantBottomNavigation(navController, sharedViewModel)
            "OWNER" -> BottomNavigation(navController, sharedViewModel)
        }
    }
}