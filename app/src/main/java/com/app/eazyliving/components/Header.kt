package com.app.eazyliving.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.eazyliving.R

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Easy",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color(android.graphics.Color.parseColor("#F48808"))
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.easyliving),
            contentDescription = "easy livng Image",
            modifier = Modifier
                .width(85.dp)
                .height(85.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Living",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color(android.graphics.Color.parseColor("#F48808"))
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}