package com.example.androidproject.ui.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MakeButton1(onClick: () -> Unit, buttonName: String) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .size(width = 200.dp, height = 60.dp)
    ) {
        Text(buttonName, color = Color.White)
    }
}