package com.example.androidproject.ui.menu
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController
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
            Button(onClick = { navController.navigate("study")}, modifier = Modifier.width(220.dp)) {

                Text("Study Cards")
            }
            Button(onClick = { navController.navigate("add")}, modifier = Modifier.width(220.dp)) {
                Text("Add Card")
            }
            Button(onClick = { navController.navigate("search")}, modifier = Modifier.width(220.dp)) {
                Text("Search Card")
            }
        }
    }
}
