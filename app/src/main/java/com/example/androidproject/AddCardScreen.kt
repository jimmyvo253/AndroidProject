package com.example.androidproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    changeMessage: (String) -> Unit,
    onSaveCard: (String, String) -> Unit
) {
    var enWord by rememberSaveable {mutableStateOf("")}
    var vnWord by rememberSaveable {mutableStateOf("")}


    LaunchedEffect(Unit) {
        changeMessage("Please, add a flash card.")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TextField(
            value = enWord,
            onValueChange = { enWord = it },
            label = { Text(stringResource(R.string.English_Label)) },
            placeholder = { Text("Enter text") },
            modifier = Modifier.semantics{contentDescription= "English Input"}.fillMaxWidth()
        )

        TextField(
            value = vnWord,
            onValueChange = { vnWord = it },
            label = { Text(stringResource(R.string.Vietnamese_Label)) },
            placeholder = { Text("Nhập nội dung") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
        )
        {

            Button(
                onClick = {
                    if (enWord.isNotBlank() && vnWord.isNotBlank()) {
                        onSaveCard(enWord, vnWord)   // ✔ writes to DB + refreshes flashCards
                        enWord = ""
                        vnWord = ""
                    }
                },
                enabled = enWord.isNotBlank() && vnWord.isNotBlank()
            ) {
                Text("Save")
            }

            Button(
                onClick =
                    {
                        enWord = ""
                        vnWord = ""
                    })
            { Text("Delete") }
        }
    }
}
