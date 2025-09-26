package com.example.androidproject
import com.example.androidproject.ui.menu.MenuScreen
import com.example.androidproject.ui.add.AddCardScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

//add Navigation Compose library
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.ui.theme.AndroidProjectTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {AnNamStudyRoomApp()}
    }
}

@Composable
fun AnNamStudyRoomApp() {
    val nav = rememberNavController()

    MaterialTheme {
        Surface {
            NavHost(navController = nav, startDestination = "menu") {
                composable("menu") {
                    MenuScreen(
                        onAddCard = { nav.navigate("add") },
                        onStudy = { /* later */ },
                        onSearch = { /* later */ }
                    )
                }
                composable("add") {
                    AddCardScreen(
                        onBack = { nav.popBackStack() },
                        onSave = { /* front-end only; maybe show a snackbar later */ }
                    )
                }
            }
        }
    }

}
