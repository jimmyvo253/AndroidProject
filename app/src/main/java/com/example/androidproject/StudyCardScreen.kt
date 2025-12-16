package com.example.androidproject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidproject.data.local.FlashCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyCardsScreen(
    lesson: List<FlashCard>,
    changeMessage: (String) -> Unit
){

    if (lesson.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No flashcards available.")
        }
        return
    }
    var index by remember { mutableStateOf(0) }
    var showVietnamese by remember { mutableStateOf(false) }

    val current = lesson[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val en = current.enCard ?: ""
        val vn = current.vnCard ?: ""

        Text(
            text = if (showVietnamese) vn else en,
            modifier = Modifier.clickable {
                showVietnamese = !showVietnamese
            }
        )

        // Show Next button only when Vietnamese is shown

        if (showVietnamese && vn.isNotBlank()) {
            Button(onClick = {
                index = (index + 1) % lesson.size
                showVietnamese = false
            }) {
                Text("Next")
            }
        }
    }

}
