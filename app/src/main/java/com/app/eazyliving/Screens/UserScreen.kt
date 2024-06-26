package com.app.eazyliving.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
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
    var showDialog by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("") } // Initialize with a default role

    val uiState = userViewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val users by userViewModel.users.collectAsState()

    LaunchedEffect(key1 = true) {
        userViewModel.fetchUsers()
    }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UserUIState.Success -> {
                if (uiState.showToast) {
                    Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
                }
            }

            is UserUIState.Error -> {
                Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header()
                Button(
                    onClick = { showDialog = true }
                ) {
                    Text(
                        text = "Add User",
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFC4E2FB), shape = MaterialTheme.shapes.medium)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "Users:",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        if (uiState is UserUIState.Loading) {
                            Text("Loading...", modifier = Modifier.padding(vertical = 10.dp))
                        }

                        AddUserDialog(
                            showDialog = showDialog,
                            onDismiss = { showDialog = false },
                            onUserAdded = { userEmail, password, role ->
                                userViewModel.addUser(userEmail, password, role)
                            },
                            selectedRole = selectedRole,
                            onRoleSelected = { role ->
                                selectedRole = role
                            }
                        )

                        LazyColumn(modifier = Modifier.weight(1f)) {
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
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    IconButton(onClick = { userViewModel.deleteUser(user.email) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete user"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            BottomNavigation(navController, sharedViewModel, selectedPage = "User")
        }
    }
}
