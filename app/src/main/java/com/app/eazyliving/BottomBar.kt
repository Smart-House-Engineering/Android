import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomBar(
    onHomeClick: () -> Unit,
    onUserClick: () -> Unit,
    onModeClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFC4E2FB))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarItem(Icons.Default.Home, "Home", onHomeClick)
        BottomBarItem(Icons.Default.Person, "User", onUserClick)
        BottomBarItem(Icons.Default.NightsStay, "Mode", onModeClick)
        BottomBarItem(Icons.AutoMirrored.Filled.Logout, "Logout", onLogoutClick)
    }
}

@Composable
fun BottomBarItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = text, Modifier.size(48.dp)) // Adjust the size as needed
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}

