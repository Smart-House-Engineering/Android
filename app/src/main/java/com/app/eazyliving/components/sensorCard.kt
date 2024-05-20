package com.app.eazyliving.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SensorCard(
    sensorName: String,
    switchStateInt: Int?,
    switchStateBool: Boolean?,
    sensorIcon: @Composable () -> Unit,
    onSwitchStateChanged: (Any) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(200.dp)
            .width(160.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFC4E2FB)),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .offset(20.dp, 20.dp)
                    .background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                sensorIcon()
            }
            Text(
                text = sensorName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.offset(20.dp),
                color = Color(0xFF838A8F)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp, start = 4.dp)
            ) {
                // Display the state for integer sensors and provide a switch for boolean sensors
                if (switchStateBool != null) {
                    val stateText = when (sensorName) {
                        "door", "window" -> if (switchStateBool) "Open" else "Closed"
                        else -> if (switchStateBool) "On" else "Off"
                    }
                    Text(
                        text = stateText,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.offset(20.dp),
                        color = Color(0xFF838A8F)
                    )
                    Switch(
                        checked = switchStateBool,
                        onCheckedChange = { newState -> onSwitchStateChanged(newState) },
                        modifier = Modifier.padding(start = 40.dp)
                    )
                } else if (switchStateInt != null) {
                    Text(
                        text = "Value: $switchStateInt",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.offset(20.dp)
                            .padding(bottom = 20.dp),
                        color = Color(0xFF838A8F)
                    )
                } else {
                    Text(
                        text = "Unknown State",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.offset(20.dp),
                        color = Color(0xFF838A8F)
                    )
                }
            }
        }
    }
}





