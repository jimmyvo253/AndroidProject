package com.example.androidproject.ui.menu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
//    onAddCard: () -> Unit,
//    onStudy: () -> Unit,
//    onSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .padding(120.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {}, modifier = Modifier.width(220.dp )) {
                Text("Study Cards")
            }
            Button(onClick = {}, modifier = Modifier.width(220.dp)) {
                Text("Add Card")
            }
            Button(onClick = {}, modifier = Modifier.width(220.dp)) {
                Text("Search Card")
            }
        }
    }
}
