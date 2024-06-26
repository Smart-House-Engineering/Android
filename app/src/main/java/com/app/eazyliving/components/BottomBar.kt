import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    selectedPage: String,
    onHomeClick: () -> Unit,
    onUserClick: () -> Unit,
    onModeClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            .background(Color(0xFFC4E2FB))
            .padding(start = 18.dp, end = 18.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarItem(
            icon = Icons.Default.Home,
            text = "Home",
            onClick = onHomeClick,
            selected = selectedPage == "Home"
        )
        BottomBarItem(
            icon = Icons.Default.Person,
            text = "User",
            onClick = onUserClick,
            selected = selectedPage == "User"
        )
        BottomBarItem(
            icon = Icons.Default.NightsStay,
            text = "Mode",
            onClick = onModeClick,
            selected = selectedPage == "Mode"
        )
        BottomBarItem(
            icon = Icons.AutoMirrored.Filled.Logout,
            text = "Logout",
            onClick = onLogoutClick,
            selected = selectedPage == "Logout"
        )
    }
}

@Composable
fun BottomBarItem(icon: ImageVector, text: String, onClick: () -> Unit, selected: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = if (selected) {
                Modifier
                    .size(40.dp)
                    .background(Color.White, shape = CircleShape)
            } else {
                Modifier.size(40.dp)
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.Black,
                modifier = Modifier.size(26.dp)
            )
        }
        Text(text = text, color = Color.Black, fontSize = 12.sp)
    }
}

