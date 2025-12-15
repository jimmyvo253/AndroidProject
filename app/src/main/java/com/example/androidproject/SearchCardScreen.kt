package com.example.androidproject.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidproject.data.local.FlashCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardList(
    flashCards: List<FlashCard>,
    selectedItem: (FlashCard) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(
            items = flashCards,
            key = { flashCard ->
                flashCard.uid
            }
        ) { flashCard ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.LightGray)
                    .padding(6.dp)
                    .clickable(onClick = {
                        selectedItem(flashCard)
                    }
                    )
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


@Composable
fun SearchCardsScreen(
    flashCards: List<FlashCard>,
    selectedItem: FlashCard?,
    onSelectItem: (FlashCard) -> Unit,
    changeMessage: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        changeMessage("This is the search screen.")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(
            modifier = Modifier.size(16.dp)
        )
        FlashCardList(
            flashCards = flashCards,
            selectedItem = onSelectItem,
        )
    }
}