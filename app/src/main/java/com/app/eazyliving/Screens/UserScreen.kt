package com.app.eazyliving.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.app.eazyliving.model.UserDetails

@Composable
fun UserScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel(), sharedViewModel: SharedViewModel) {
    var showDialog by remember { mutableStateOf(false)
    }
    val uiState = userViewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val users by userViewModel.users.collectAsState()

    LaunchedEffect(key1 = true) {
        userViewModel.fetchUsers()
    }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UserUIState.Success -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()

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
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
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
                Text(
                    text = "Add User",
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            if (uiState is UserUIState.Loading) {
                Text("Loading...", modifier = Modifier.padding(vertical = 10.dp))
            }
            AddUserDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onUserAdded = { userEmail, password, role ->
                    userViewModel.addUser(userEmail, password, role)
                }
            )
            LazyColumn {
                items(users) { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${user.email} (${user.role})",
                            style = MaterialTheme.typography.bodySmall
                        )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("Back", style = TextStyle(fontSize = 18.sp))
            }
            BottomNavigation(navController, sharedViewModel)
        }
    }

}