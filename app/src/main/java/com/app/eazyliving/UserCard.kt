package com.app.eazyliving
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.eazyliving.model.UserPayload
import com.app.eazyliving.network.Cookies.TokenManager
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun UserCard() {

    /*val context = LocalContext.current
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
    }*/

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(100.dp)
            .width(260.dp)
    ) {
        Column (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color(0xFFC4E2FB))
                .fillMaxWidth()
                .fillMaxSize()){
            /*payload?.let { (email,
             role) ->
                Text(
                    text = "User: $email + \"User Role: $role\"",

                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(16.dp)
                )*/

            Row(
                modifier =
                Modifier
                    .padding(bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "User Email",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color(0xFF393939))
                Text(
                    text = "User Role",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color(0xFF393939))
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                val todayDate = rememberUpdatedState(getTodayDate())

                Text(
                    text = todayDate.value,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color(0xFF393939)
                )

                val currentTime = rememberUpdatedState(getFormattedTime())

                Text(
                    text = "Time: ${currentTime.value}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    color = Color(0xFF393939)
                )
            }
            }
        }
    }

@Composable
private fun getTodayDate(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("EEEE',' d  MMMM", Locale.getDefault())
    return dateFormat.format(currentDate)
}

@Composable
private fun getFormattedTime(): String {
    val currentTime = Calendar.getInstance().time
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(currentTime)
}
