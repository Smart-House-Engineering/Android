package com.app.eazyliving.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TenantBottomBar(
    selectedPage: String,
    onHomeClick: () -> Unit,
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
        TenantBottomBarItem(
            icon = Icons.Default.Home,
            text = "Home",
            onClick = onHomeClick,
            selected = selectedPage == "Home"
        )
        TenantBottomBarItem(
            icon = Icons.Default.NightsStay,
            text = "Mode",
            onClick = onModeClick,
            selected = selectedPage == "Mode"
        )
        TenantBottomBarItem(
            icon = Icons.AutoMirrored.Filled.Logout,
            text = "Logout",
            onClick = onLogoutClick,
            selected = selectedPage == "Logout"
        )
    }
}

@Composable
fun TenantBottomBarItem(icon: ImageVector, text: String, onClick: () -> Unit, selected: Boolean) {
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
                    .size(48.dp)
                    .background(Color.White, shape = CircleShape)
            } else {
                Modifier.size(48.dp)
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (selected) Color.Black else Color.White,
                modifier = Modifier.size(24.dp) // Adjust the size as needed
            )
        }
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}
