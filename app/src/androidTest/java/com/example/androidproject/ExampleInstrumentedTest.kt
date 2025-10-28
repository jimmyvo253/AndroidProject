package com.example.androidproject

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.androidproject.navigation.Navigator
import com.example.androidproject.ui.theme.AndroidProjectTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            AndroidProjectTheme {
            Navigator(navController)
            }
        }
        assertEquals("menu", navController.currentDestination?.route)

    }
    @Test
    fun menuHasAddButtonTest () {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            AndroidProjectTheme {
                Navigator(navController)
            }
        }
        composeTestRule.onNodeWithText("Add a Card").assertIsDisplayed()
    }
    @Test
    fun clickonAddCard (){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            AndroidProjectTheme {
                Navigator(navController)
            }
        }
        composeTestRule.onNodeWithText("Add a Card").performClick()
        composeTestRule
    }
}

