package com.example.androidproject

import android.R.id.checkbox
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.testing.TestNavigatorState
import com.example.androidproject.data.local.FlashCard
@Composable
fun SearchCardsScreen(
    navigateToList: () -> Unit,
    changeMessage: (String) -> Unit
) {
    LaunchedEffect(Unit) { changeMessage("Search Screen") }
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
                value = vnText,
                onValueChange = { vnText = it },
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

//        Button(
//            modifier = Modifier
//                .semantics{
//
//            },
//
//        )

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//
//            Spacer(modifier = Modifier.width(8.dp))
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = { Text("Search") },
//                singleLine = true
//            )
        }

        Spacer(modifier = Modifier.height(8.dp))

    }
