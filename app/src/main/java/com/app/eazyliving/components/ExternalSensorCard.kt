package com.app.eazyliving.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Locale

/*
    Sensor card for displaying sensor data on the External Screen
 */

@Composable
fun ExternalSensorCard(
    sensorName: String,
    switchStateInt: Int?,
    switchStateBool: Boolean?
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(70.dp)
            .width(180.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (sensorName == "gasSensor") Color(0xFFF6DCBD) else Color(0xFFC4E2FB)), // Change background color for gasSensor
            verticalArrangement = Arrangement.Center, // Center the content vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // Push the items to the start and end of the row
                modifier = Modifier
                    .fillMaxWidth() // Make the row take up the full width of the card
                    .padding(horizontal = 4.dp) // Add some horizontal padding
            ) {
                Text(
                    text = sensorName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }, // Capitalize the first letter of the sensor name
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 8.dp), // Add padding to the left of the sensor name
                    color = Color(0xFF838A8F)
                )
                // Display the state for integer sensors and provide a switch for boolean sensors
                if (switchStateBool != null) {
                    val stateText = when {
                        sensorName in listOf("Window", "Door") -> if (switchStateBool) "Open" else "Closed"
                        else -> if (switchStateBool) "On" else "Off"
                    }
                    Text(
                        text = stateText, // Display 'Open'/'Closed' for Window and Door, 'On'/'Off' for others
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp), // Add padding to the right of the sensor state
                        color = if (switchStateBool) Color.Green else Color.Red // Set color based on state
                    )
                } else if (switchStateInt != null) {
                    Text(
                        text = "$switchStateInt", // Remove 'Value' from the text
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp), // Add padding to the right of the sensor state
                        color = if (switchStateInt == 1) Color.Green else Color.Red // Set color based on state
                    )
                } else {
                    Text(
                        text = "Unknown State",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp), // Add padding to the right of the sensor state
                        color = Color(0xFF838A8F)
                    )
                }
            }
        }
    }
}



/*
@Composable
fun ExternalSensorCard(
    sensorName: String,
    switchStateInt: Int?,
    switchStateBool: Boolean?,
    //sensorIcon: @Composable () -> Unit
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
            /*
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(20.dp, 20.dp)
                    .background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            )


            {
                sensorIcon()
            }
            */

            Text(
                text = sensorName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.offset(20.dp),
                color = Color(0xFF838A8F)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                // Display the state for integer sensors and provide a switch for boolean sensors
                if (switchStateBool != null) {
                    Text(
                        text = "State: ${if (switchStateBool) "On" else "Off"}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.offset(20.dp),
                        color = Color(0xFF838A8F)
                    )
                } else if (switchStateInt != null) {
                    Text(
                        text = "Value: $switchStateInt",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.offset(20.dp),
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
*/