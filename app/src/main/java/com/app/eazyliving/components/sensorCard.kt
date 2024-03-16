package com.app.eazyliving.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
                    .size(40.dp)
                    .offset(20.dp, 20.dp)
                    .background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Icon", color = Color(0xFF838A8F))
            }
            Text(
                text = "Name",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .offset(20.dp),
                color = Color(0xFF838A8F)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "on/off",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.offset(20.dp),
                    color = Color(0xFF838A8F)
                )
                Switch(
                    checked = switchState,
                    onCheckedChange = { newState -> onSwitchStateChanged(newState) },
                    modifier = Modifier
                        .padding(start = 40.dp)
                )
            }
        }
    }
}

