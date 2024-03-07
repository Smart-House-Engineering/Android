package com.app.eazyliving.Screens

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.eazyliving.model.UserPayload
import com.app.eazyliving.network.Cookies.TokenManager
import com.google.gson.Gson

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var payload by remember { mutableStateOf<Pair<String, String>?>(null) }

    // This will run once when the composable is loaded
    LaunchedEffect(key1 = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decodedData = TokenManager.getDecodedTokenData(context)
            decodedData?.second?.let {
                val gson = Gson()
                val userPayload =
                    gson.fromJson(it, UserPayload::class.java) // Parsing the JSON into UserPayload
                payload =
                    Pair(userPayload.user.email, userPayload.user.role) // Extract email and role
            }
        } else {
            // Handle older Android versions or alert the user
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))
                payload.let {
                    // Display the user information if available
                    if (it != null) {
                        Text(
                            text = "Welcome ${it.first}! Your role is ${it.second}.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }


