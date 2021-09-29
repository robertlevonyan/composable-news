package com.robertlevonyan.composable.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainScreen
import com.robertlevonyan.composable.newsapp.ui.screens.news.NewsScreen
import com.robertlevonyan.composable.newsapp.ui.screens.search.SearchScreen
import com.robertlevonyan.composable.newsapp.ui.screens.splash.SplashScreen

const val NAV_NEWS_ITEM = "navNewsItem"

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SplashScreen.name,
    ) {
        composable(route = NavigationScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(route = NavigationScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }
        composable(route = "${NavigationScreens.NewsScreen.name}/{$NAV_NEWS_ITEM}") { navEntry ->
            NewsScreen(
                navController = navController,
                newsItemTitle = navEntry.arguments?.getString(NAV_NEWS_ITEM),
            )
        }
        composable(route = NavigationScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}

sealed class NavigationScreens(val name: String) {
    object SplashScreen : NavigationScreens("splash_screen")
    object MainScreen : NavigationScreens("main_screen")
    object NewsScreen : NavigationScreens("news_screen")
    object SearchScreen : NavigationScreens("search_screen")
}
