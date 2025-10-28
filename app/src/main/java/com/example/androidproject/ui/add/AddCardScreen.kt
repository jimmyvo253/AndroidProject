package com.example.androidproject.ui.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(navController: NavController) {
    var clickOnAdd by remember { mutableStateOf(false) }
    //var enWord = ""
    //var vnWord = ""
    var enWord by remember { mutableStateOf("") }
    var vnWord by remember { mutableStateOf("") }
    //var enWord by rememberSaveable { mutableStateOf("") }
    //var vnWord by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Cards") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black   // or MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = enWord,
                onValueChange = { enWord = it },
                modifier = Modifier.semantics { contentDescription = "English String" },
                label = { Text("en") }
            )
            TextField(
                value = vnWord,
                onValueChange = { vnWord = it },
                label = { Text("vn") }
            )

            if (clickOnAdd) {
                Text("Adding card [$enWord, $vnWord] ...")
            }
            //ClickCounter(
            //    clicks = num,
            //    onClick = {num = num + 1}
            //)
            Button(onClick = {
                clickOnAdd = true
            })
            {
                Text("Add")
            }
        }
    }

}
//@Composable
//fun ClickCounter(clicks: Int, onClick: () -> Unit) {
//    Button(onClick = onClick) {
//        Text("I've been clicked $clicks times")
//    }
//}
//}
//@Composable
//fun AddCardScreen(navController: NavController) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Add a Card") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Text("Back")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            contentAlignment = Alignment.Center
//        ) { }
//    }
//}
//
//@Composable
//fun AddCardScreen(navController: NavController
//) {
//    var direction by rememberSaveable { mutableStateOf("EN→VI") }
//    var en by rememberSaveable { mutableStateOf("") }
//    var vi by rememberSaveable { mutableStateOf("") }
//    var notes by rememberSaveable { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(36.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        // Top row: Back + Title
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
////            TextButton(onClick = onBack) { Text("Back") }
//            Text("Add Card")
//            Spacer(Modifier.width(48.dp)) // just to balance layout visually
//        }
//
//        // Direction (two radios)
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            DirectionRadio(
//                selected = direction == "EN→VI",
//                label = "EN→VI"
//            ) { direction = "EN→VI" }
//
//            DirectionRadio(
//                selected = direction == "VI→EN",
//                label = "VI→EN"
//            ) { direction = "VI→EN" }
//        }
//
//        // Fields
//        if (direction == "EN→VI") {
//            OutlinedTextField(
//                value = en, onValueChange = { en = it },
//                label = { Text("English") }, modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = vi, onValueChange = { vi = it },
//                label = { Text("Vietnamese") }, modifier = Modifier.fillMaxWidth()
//            )
//        } else {
//            OutlinedTextField(
//                value = vi, onValueChange = { vi = it },
//                label = { Text("Vietnamese") }, modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = en, onValueChange = { en = it },
//                label = { Text("English") }, modifier = Modifier.fillMaxWidth()
//            )
//        }
//
//        OutlinedTextField(
//            value = notes, onValueChange = { notes = it },
//            label = { Text("Notes (optional)") },
//            modifier = Modifier.fillMaxWidth(),
//            minLines = 2
//        )
//
//        // Buttons
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            Button(onClick = {
//                // FRONT-END ONLY: just validate locally
//                val ok = if (direction == "EN→VI") en.isNotBlank() && vi.isNotBlank()
//                else en.isNotBlank() && vi.isNotBlank()
//                if (ok) {
//                    onSave()
////                    onBack()  // go back after “saving”
//                }
//            }) {
//                Text("Save")
//            }
//            TextButton(onClick = ) {
//                Text("Cancel")
//            }
//        }
//    }
//}
//
//@Composable
//private fun DirectionRadio(selected: Boolean, label: String, onSelect: () -> Unit) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(6.dp)
//    ) {
//        RadioButton(selected = selected, onClick = onSelect)
//        Text(label)
//    }
//}
