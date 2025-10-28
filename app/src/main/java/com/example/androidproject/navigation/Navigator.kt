package com.example.androidproject.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidproject.ui.add.AddCardScreen
import com.example.androidproject.ui.menu.MenuScreen
import com.example.androidproject.ui.search.SearchCardsScreen
import com.example.androidproject.ui.study.StudyCardScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(navController : NavHostController) {
//    val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "menu",
        ) {
            composable("menu") { MenuScreen(navController) }
            composable("study") { StudyCardScreen(navController) }
            composable("add") { AddCardScreen(navController) }
            composable("search") { SearchCardsScreen(navController) }
        }
    }