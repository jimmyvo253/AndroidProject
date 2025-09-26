package com.example.androidproject.ui.menu
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onAddCard: () -> Unit,
    onStudy: () -> Unit,
    onSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(120.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onStudy, modifier = Modifier.width(220.dp)) {
                Text("Study Cards")
            }
            Button(onClick = onAddCard, modifier = Modifier.width(220.dp)) {
                Text("Add Card")
            }
            Button(onClick = onSearch, modifier = Modifier.width(220.dp)) {
                Text("Search Card")
            }
        }
    }
}
