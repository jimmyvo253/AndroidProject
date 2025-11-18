package com.example.androidproject.ui.study

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.androidproject.data.local.FlashCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyCardsScreen(
    flashCards: List<FlashCard>,
    changeMessage: (String) -> Unit
){

}
