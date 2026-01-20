package com.example.androidproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androidproject.data.local.FlashCard

@Composable
fun SearchCardsScreen(
    navigateToList: () -> Unit,
    changeMessage: (String) -> Unit,
    onSearchCards: (String, String, Boolean, Boolean) -> Unit
) {
    LaunchedEffect(Unit) { changeMessage("Search Screen") }
    var searchResults by remember { mutableStateOf<List<FlashCard>>(emptyList()) }
    var enText by remember { mutableStateOf("") }
    var vnText by remember { mutableStateOf("") }
    var englishChecked by remember { mutableStateOf(false) }
    var vietnameseChecked by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Search Bar
            // Search TextField
            Checkbox(
                checked = englishChecked,
                onCheckedChange = {englishChecked = it},
                modifier = Modifier.semantics {contentDescription ="EnglishCheckbox"}
            )
            OutlinedTextField(
                value = enText,
                onValueChange = { enText = it },
                modifier = Modifier.weight(1f).semantics{contentDescription = "english"},
                placeholder = { Text("English") },
                singleLine = true
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Search Bar
            // Search TextField
            Checkbox(
                checked = vietnameseChecked,
                onCheckedChange = {vietnameseChecked = it},
                modifier = Modifier.semantics {contentDescription ="vietnameseCheckbox"}
            )
            OutlinedTextField(
                value = vnText,
                onValueChange = { vnText = it },
                modifier = Modifier.weight(1f).semantics{contentDescription = "vietnamese"},
                placeholder = { Text("Tiếng Việt") },
                singleLine = true
            )

        }

        Button(
            onClick = {
                focusManager.clearFocus()
                // Search logic will be wired via Navigator
                onSearchCards(
                    enText,
                    vnText,
                    englishChecked,
                    vietnameseChecked
                )
                navigateToList()
            }
        ) {
            Text("Search")
        }
        }

        Spacer(modifier = Modifier.height(8.dp))

    }
