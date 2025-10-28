package com.example.androidproject.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidproject.ui.button.MakeButton1

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        MakeButton1(onClick = { navController.navigate("study") }, "Study Cards")
        Button(
            modifier = Modifier
                .padding(16.dp)
                .size(width = 200.dp, height = 60.dp)
                .semantics { contentDescription = "navigateToStudyCards" }, //
            onClick = { navController.navigate("study") }
        ) {
            Text("Study Cards", color = Color.White)
        }
        MakeButton1(onClick = { navController.navigate("add") }, "Add a Card")
        MakeButton1(onClick = { navController.navigate("search") }, "Search Cards")
    }
}
