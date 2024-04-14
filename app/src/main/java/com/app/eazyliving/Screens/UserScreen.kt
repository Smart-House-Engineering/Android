package com.app.eazyliving.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.eazyliving.ViewModel.SharedViewModel
import com.app.eazyliving.ViewModel.UserUIState
import com.app.eazyliving.ViewModel.UserViewModel
import com.app.eazyliving.components.AddUserDialog
import com.app.eazyliving.components.Header

@Composable
fun UserScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel(), sharedViewModel: SharedViewModel) {
    var showDialog by remember { mutableStateOf(false)
    }
    val uiState = userViewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is UserUIState.Success -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
                navController.navigate("HomeScreen"){
                popUpTo("UserScreen") { inclusive = true } // Adjust based on your actual navigation setup
            }
            }
            is UserUIState.Error -> {
                Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 30.dp)
    ) {
        Header()
        Text(
            text = "User Screen",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Add User",
                style = TextStyle(fontSize = 18.sp)
            )
        }
        if (uiState is UserUIState.Loading) {
            Text("Loading...", modifier = Modifier.padding(vertical = 10.dp))
        }
        AddUserDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onUserAdded = { userEmail, password,role ->
                userViewModel.addUser(userEmail, password, role)
            }
        )

        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            BottomNavigation(navController, sharedViewModel)
        }
    }

}