package com.robertlevonyan.composable.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainScreen
import com.robertlevonyan.composable.newsapp.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SplashScreen.name,
    ) {
        composable(NavigationScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(NavigationScreens.MainScreen.name) {
            MainScreen()
        }
    }
}

sealed class NavigationScreens(val name: String) {
    object SplashScreen : NavigationScreens("splash_screen")
    object  MainScreen : NavigationScreens("main_screen")
}
