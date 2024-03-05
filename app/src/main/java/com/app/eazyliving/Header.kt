package com.app.eazyliving

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.easyliving),
            contentDescription = "easy livng Image",
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight(0.1f)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Living",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}