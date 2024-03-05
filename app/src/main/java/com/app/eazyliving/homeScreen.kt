package com.app.eazyliving

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.eazyliving.ui.theme.EazyLivingTheme

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Header()

        var switchState by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            for (index in 0 until 4) {
                SensorCard(
                    switchState = switchState,
                    onSwitchStateChanged = { newState -> switchState = newState }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EazyLivingTheme {
        HomeScreen()
    }
}