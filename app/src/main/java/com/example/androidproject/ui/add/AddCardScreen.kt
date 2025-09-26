package com.example.androidproject.ui.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCardScreen(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var direction by rememberSaveable { mutableStateOf("EN→VI") }
    var en by rememberSaveable { mutableStateOf("") }
    var vi by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top row: Back + Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
//            TextButton(onClick = onBack) { Text("Back") }
            Text("Add Card")
            Spacer(Modifier.width(48.dp)) // just to balance layout visually
        }

        // Direction (two radios)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DirectionRadio(
                selected = direction == "EN→VI",
                label = "EN→VI"
            ) { direction = "EN→VI" }

            DirectionRadio(
                selected = direction == "VI→EN",
                label = "VI→EN"
            ) { direction = "VI→EN" }
        }

        // Fields
        if (direction == "EN→VI") {
            OutlinedTextField(
                value = en, onValueChange = { en = it },
                label = { Text("English") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = vi, onValueChange = { vi = it },
                label = { Text("Vietnamese") }, modifier = Modifier.fillMaxWidth()
            )
        } else {
            OutlinedTextField(
                value = vi, onValueChange = { vi = it },
                label = { Text("Vietnamese") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = en, onValueChange = { en = it },
                label = { Text("English") }, modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = notes, onValueChange = { notes = it },
            label = { Text("Notes (optional)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        // Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = {
                // FRONT-END ONLY: just validate locally
                val ok = if (direction == "EN→VI") en.isNotBlank() && vi.isNotBlank()
                else en.isNotBlank() && vi.isNotBlank()
                if (ok) {
                    onSave()
//                    onBack()  // go back after “saving”
                }
            }) {
                Text("Save")
            }
            TextButton(onClick = onBack) {
                Text("Cancel")
            }
        }
    }
}

@Composable
private fun DirectionRadio(selected: Boolean, label: String, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Text(label)
    }
}
