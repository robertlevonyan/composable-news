package com.robertlevonyan.composable.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainScreen
import com.robertlevonyan.composable.newsapp.ui.screens.news.NewsScreen
import com.robertlevonyan.composable.newsapp.ui.screens.search.SearchScreen
import com.robertlevonyan.composable.newsapp.ui.screens.sources.SourcesScreen
import com.robertlevonyan.composable.newsapp.ui.screens.splash.SplashScreen

const val NAV_NEWS_ITEM = "navNewsItem"
const val NAV_SOURCE_ID = "navSourceId"
const val NAV_SOURCE_NAME = "navSourceName"

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
        composable(route = NavigationScreens.NewsScreen.name) {
            navController.previousBackStackEntry?.arguments?.getParcelable<NewsItem?>(NAV_NEWS_ITEM)?.let { currentNews ->
                NewsScreen(
                    navController = navController,
                    newsItem = currentNews,
                )
            }
        }
        composable(route = NavigationScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(
            route = "${NavigationScreens.SourcesScreen.name}/{$NAV_SOURCE_ID}/{$NAV_SOURCE_NAME}",
            arguments = listOf(
                navArgument(name = NAV_SOURCE_ID) { type = NavType.StringType },
                navArgument(name = NAV_SOURCE_NAME) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            SourcesScreen(
                navController = navController,
                sourceId = backStackEntry.arguments?.getString(NAV_SOURCE_ID) ?: "",
                sourceName = backStackEntry.arguments?.getString(NAV_SOURCE_NAME) ?: "",
            )
        }
    }
}

sealed class NavigationScreens(val name: String) {
    object SplashScreen : NavigationScreens("splash_screen")
    object MainScreen : NavigationScreens("main_screen")
    object NewsScreen : NavigationScreens("news_screen")
    object SearchScreen : NavigationScreens("search_screen")
    object SourcesScreen : NavigationScreens("sources_screen")
}
