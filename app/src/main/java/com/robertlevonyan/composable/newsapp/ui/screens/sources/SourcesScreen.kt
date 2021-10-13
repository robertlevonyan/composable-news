package com.robertlevonyan.composable.newsapp.ui.screens.sources

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.ui.components.ItemHeadingText
import com.robertlevonyan.composable.newsapp.ui.components.SearchNewsItem
import com.robertlevonyan.composable.newsapp.ui.navigation.NAV_NEWS_ITEM
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.theme.HalfPadding

@Composable
fun SourcesScreen(
    navController: NavController,
    sourceId: String,
    sourceName: String,
) {
    if (sourceId.isEmpty()) {
        navController.popBackStack()
        return
    }

    val sourcesViewModel: SourcesViewModel = hiltViewModel()
    val sourcesResults: LazyPagingItems<NewsItem> = sourcesViewModel.getNewsBySource(sourceId).collectAsLazyPagingItems()

    Scaffold(
        content = {
            SourcesScreenContent(
                navController = navController,
                sourceName = sourceName,
                sourcesResults = sourcesResults,
            )
        }
    )
}

@Composable
private fun SourcesScreenContent(
    navController: NavController,
    sourceName: String,
    sourcesResults: LazyPagingItems<NewsItem>
) {
    LazyColumn(
        modifier = Modifier
            .padding(all = HalfPadding)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        item {
            ItemHeadingText(text = "All results for $sourceName source")
        }
        items(count = sourcesResults.itemCount) { index ->
            sourcesResults[index]?.let { newsItem ->
                SearchNewsItem(
                    newsItem = newsItem,
                    onNewsItemClick = { currentNewsItem ->
                        navController.currentBackStackEntry?.arguments?.putParcelable(NAV_NEWS_ITEM, currentNewsItem)
                        navController.navigate(NavigationScreens.NewsScreen.name)
                    },
                )
            }
        }
    }
}
