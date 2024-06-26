package com.app.eazyliving.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddUserDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onUserAdded: (String, String, String) -> Unit,
    selectedRole: String,
    onRoleSelected: (String) -> Unit
) {
    if (showDialog) {
        var expanded by remember { mutableStateOf(false) }
        val roles = listOf("TENANT", "EXTERNAL")
        val focusManager = LocalFocusManager.current

        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            Surface(
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp),
                color = Color(0xFFC4E2FB),
                shape = RoundedCornerShape(5)
            ) {
                Column(
                    modifier = Modifier.padding(25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add User",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    var userEmail by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var confirmPassword by remember { mutableStateOf("") }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = userEmail,
                        onValueChange = { userEmail = it },
                        label = { Text("UserEmail") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password
                        ),
                    )
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                    )

                    if (password != confirmPassword && confirmPassword.isNotBlank()) {
                        Text(
                            text = "Passwords don't match",
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (selectedRole.isEmpty()) "Role: Select role" else "Role: $selectedRole"
                            )
                            IconButton(
                                onClick = { expanded = true },
                                modifier = Modifier.size(36.dp) // Increase the size of the arrow icon
                            ) {
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand Role Dropdown")
                            }
                        }
                        Box(
                            modifier = Modifier.background(Color.White)
                        ) {
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                roles.forEach { role ->
                                    ClickableText(
                                        text = AnnotatedString(role),
                                        onClick = {
                                            onRoleSelected(role)
                                            expanded = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(12.dp),
                                        style = TextStyle(fontSize = 16.sp)
                                    )
                                }
                            }
                        }
                    }



                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                if (password == confirmPassword) {
                                    onUserAdded(userEmail, password, selectedRole)
                                    userEmail = ""
                                    password = ""
                                    confirmPassword = ""
                                    onDismiss()
                                }
                            }
                        ) {
                            Text("Register")
                        }
                    }
                }
            }
        }
    }
}

