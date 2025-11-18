package com.example.androidproject

import Navigator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.androidproject.data.local.MenuDatabase
import com.example.androidproject.ui.theme.AndroidProjectTheme

class MainActivity : ComponentActivity() {

    private lateinit var db: MenuDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Build Room DB once, outside Compose
        db = Room.databaseBuilder(
            applicationContext,
            MenuDatabase::class.java,
            "menuDatabase"
        ).build()

        setContent {
            AndroidProjectTheme {
                val navController = rememberNavController()
                // Pass DAO into Navigator
                Navigator(
                    navController = navController,
                    flashCardDao = db.flashCardDao()
                )
            }
        }
    }
}
