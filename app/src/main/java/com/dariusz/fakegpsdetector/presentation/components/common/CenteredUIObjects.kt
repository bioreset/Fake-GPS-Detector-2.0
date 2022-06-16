package com.dariusz.fakegpsdetector.presentation.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.wrapContentWidth(
                Alignment.CenterHorizontally
            )
        )
        Text("It can take a longer while...", modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 2.dp))
    }
}

@Composable
fun CenteredText(
    text: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            modifier = Modifier.wrapContentWidth(
                Alignment.CenterHorizontally
            ),
            color = Color.Red
        )
    }
}