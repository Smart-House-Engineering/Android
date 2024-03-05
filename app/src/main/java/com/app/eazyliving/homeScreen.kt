package com.app.eazyliving

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.eazyliving.ui.theme.EazyLivingTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Header()

        val todayDate = rememberUpdatedState(getTodayDate())

        Text(
            text = todayDate.value,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        val currentTime = rememberUpdatedState(getFormattedTime())

        Text(
            text = "Time: ${currentTime.value}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        var switchState by remember { mutableStateOf(false) }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(2) { rowIndex ->
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(2) { columnIndex ->
                        SensorCard(
                            switchState = switchState,
                            onSwitchStateChanged = { newState -> switchState = newState }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getTodayDate(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(currentDate)
}

@Composable
private fun getFormattedTime(): String {
    val currentTime = Calendar.getInstance().time
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(currentTime)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EazyLivingTheme {
        HomeScreen()
    }
}