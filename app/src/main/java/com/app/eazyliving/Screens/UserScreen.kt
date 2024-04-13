package com.app.eazyliving.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.eazyliving.components.AddUserDialog
import com.app.eazyliving.components.Header

@Composable
fun UserScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false)
    }
    Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        Text(
            text = "User Screen",
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Add User")
        }
        AddUserDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onUserAdded = { username, password ->
                println("User added: username=$username, password=$password")
                navController.navigate("other_route")
            }
        )
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            BottomNavigation(navController)
        }
    }

}