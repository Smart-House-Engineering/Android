package com.app.eazyliving.Screens

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.app.eazyliving.network.Cookies.TokenManager

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current // Use LocalContext.current to get the context
    var payload by remember { mutableStateOf<String?>(null) }

    // This will run once when the composable is loaded
    LaunchedEffect(key1 = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decodedData = TokenManager.getDecodedTokenData(context)
            payload = decodedData?.second // Assign the payload to be displayed
        } else {
            // Handle older Android versions or alert the user
            payload = "Unsupported Android version"
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (payload != null) {
            // Display the payload
            Text(text = "Welcome to the Home Screen!\nUser Info: $payload", style = MaterialTheme.typography.bodyMedium)
        } else {
            Text(text = "Welcome to the Home Screen!", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


