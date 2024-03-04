package com.app.eazyliving
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
import androidx.compose.ui.unit.dp

@Composable
fun SensorCard(
    switchState: Boolean,
    onSwitchStateChanged: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(200.dp)
            .width(160.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .padding(top = 20.dp, start = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Icon", color = Color.Black)
            }
            Text(
                text = "Name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "on/off",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
                Switch(
                    checked = switchState,
                    onCheckedChange = { newState -> onSwitchStateChanged(newState) },
                    modifier = Modifier
                        .padding(start = 20.dp)

                )
            }
        }
    }
}

