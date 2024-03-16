package com.app.eazyliving

import BottomBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.eazyliving.ViewModel.LoginViewModel

@Composable
fun HomeScreen(navController: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
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
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
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
        BottomBar(
            onHomeClick = { /* Handle Home click */ },
            onUserClick = { /* Handle User click */ },
            onModeClick = { /* Handle Mode click */ },
            onLogoutClick = { /* Handle Logout click */ }
        )
    }
}

