package com.app.eazyliving

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.eazyliving.ViewModel.LoginViewModel

@Composable
fun HomeScreen(navController: LoginViewModel) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Header()
        UserCard()
        var switchState by remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(4) { rowIndex ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
