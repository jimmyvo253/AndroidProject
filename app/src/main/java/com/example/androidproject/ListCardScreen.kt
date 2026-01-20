package com.example.androidproject

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
    onEdit: (FlashCard) -> Unit,
    onDelete: (FlashCard) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(
            items = flashCards,
            key = { it.uid }
        ) { flashCard ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Blue)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // --- Card text ---
                Row {
                    Text(flashCard.enCard ?: "")
                    Text(" = ")
                    Text(flashCard.vnCard ?: "")
                }

                // --- Actions ---
                Row {
                    Text(
                        text = "Edit",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { onEdit(flashCard) },
                        color = Color.Blue
                    )

                    Text(
                        text = "Delete",
                        modifier = Modifier.clickable { onDelete(flashCard) },
                        color = Color.Red
                    )
                }
            }
        }
    }
}