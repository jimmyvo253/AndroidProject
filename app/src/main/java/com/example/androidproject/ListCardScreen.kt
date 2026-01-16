package com.example.androidproject

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidproject.data.local.FlashCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardList(
    flashCards: List<FlashCard>,
    navigationSelectedItem: (FlashCard) -> Unit,   // 👈 rename + meaning changed
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(
            items = flashCards,
            key = { flashCard -> flashCard.uid }
        ) { flashCard ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Blue)
                    .padding(6.dp)
                    .clickable {
                        navigationSelectedItem(flashCard)   // 👈 when you click a row
                    }
            ) {
                Column(modifier = Modifier.padding(6.dp))
                { Text(flashCard.enCard.toString()) }
                Column(modifier = Modifier.padding(6.dp)) { Text(" = ") }
                Column(modifier = Modifier.padding(6.dp))
                { Text(flashCard.vnCard.toString()) }
            }
        }
    }
}