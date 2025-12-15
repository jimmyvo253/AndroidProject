package com.example.androidproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidproject.data.local.FlashCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCardScreen(
    flashCard: FlashCard?,
    onDelete: (FlashCard) -> Unit
) {
    if (flashCard == null) {
        Text("No card selected")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "English: ${flashCard.enCard}")
        Text(text = "Vietnamese: ${flashCard.vnCard}")

        Button(onClick = { onDelete(flashCard) }) {
            Text("Delete")
        }
    }
}