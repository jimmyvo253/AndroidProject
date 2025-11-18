package com.example.androidproject.ui.add

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
import com.example.androidproject.R

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
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddCardScreen(changeMessage: (String) -> Unit = {}) {
////    var clickOnAdd by remember { mutableStateOf(false) }
////    //var enWord = ""
////    //var vnWord = ""
////    var enWord by remember { mutableStateOf("") }
////    var vnWord by remember { mutableStateOf("") }
//    var enWord by rememberSaveable { mutableStateOf("") }
//    var vnWord by rememberSaveable { mutableStateOf("") }
//
//    val word = remember { mutableStateListOf<Pair<String, String>>() }
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(horizontal = 16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally) {
//        TextField(
//            value = enWord,
//            onValueChange = { enWord = it },
//            modifier = Modifier.semantics { contentDescription = "English String" },
//            label = { Text(stringResource(R.string.English_Label)) },
//            placeholder = {Text("Enter English word")},
//        )
//        TextField(
//            value = vnWord,
//            onValueChange = { vnWord = it },
//            label = { Text(stringResource(R.string.Vietnamese_Label)) },
//            placeholder = {Text("Nhập từ Tiếng Việt")}
//        )
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
//        )
//        {
//
//            Button(
//                onClick =
//                    {
//                        if (vnWord.isNotBlank() && enWord.isNotBlank()) {
//                            word.add(enWord to vnWord) //Make a pair of words
//                            enWord = "" // Clear the text field
//                            vnWord = "" // Clear the text field
//
//                        }
//                        // Only show up the "Save" button when fulfilled En and Vie
//                    }, enabled = vnWord.isNotBlank() && enWord.isNotBlank()
//            )
//            { Text("Save") }
//
//            Button(
//                onClick =
//                    {
//                        enWord = ""
//                        vnWord = ""
//                    })
//            { Text("Delete") }
//        }
//
//        Column {
//            word.forEach { (english, vietnamese) ->
//                Text("English: $english - Vietnamese: $vietnamese")
//            }
//        }
//    }
//}
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
