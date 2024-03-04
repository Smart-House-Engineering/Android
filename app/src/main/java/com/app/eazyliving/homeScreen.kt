package com.app.eazyliving

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.eazyliving.ui.theme.EazyLivingTheme

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Easy",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.easyliving),
            contentDescription = "easy livng Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Living",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        var switchState by remember { mutableStateOf(false) }

        SensorCard(
            switchState = switchState,
            onSwitchStateChanged = { newState -> switchState = newState }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EazyLivingTheme {
        HomeScreen()
    }
}