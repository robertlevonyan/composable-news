package com.robertlevonyan.composable.newsapp.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.ui.components.*
import com.robertlevonyan.composable.newsapp.ui.navigation.NAV_NEWS_ITEM
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.theme.*

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    navController: NavController,
    newsItem: NewsItem,
) {
    Scaffold(
        content = {
            NewsScreenContent(
                newsViewModel = newsViewModel,
                navController = navController,
                currentNews = newsItem,
            )
        }
    )
}

@Composable
fun NewsScreenContent(
    newsViewModel: NewsViewModel,
    navController: NavController,
    currentNews: NewsItem,
) {
    newsViewModel.getSourceNews(newsItem = currentNews)

    val sourceNews by newsViewModel.sourceNews.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        SelectedNews(currentNews = currentNews, navController = navController)
        SectionHeadingText(text = "${stringResource(id = R.string.label_same_source)} (${currentNews.source.name})")
        PublisherNews(sourceNews = sourceNews, navController = navController)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun SelectedNews(currentNews: NewsItem, navController: NavController) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = SectionSize),
            painter = rememberImagePainter(data = currentNews.image),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        BackButton(navController = navController)
    }

    ItemHeadingText(
        text = currentNews.title ?: "",
        textColor = if (isSystemInDarkTheme()) WhitePure else BlackPure,
    )

    GeneralText(
        text = currentNews.content ?: "",
        textColor = if (isSystemInDarkTheme()) White else Black,
    )
}

@Composable
private fun PublisherNews(sourceNews: List<NewsItem>, navController: NavController) {
    if (sourceNews.isEmpty()) {
        ShowLoading(sectionHeight = SectionSize)
    } else {
        LazyRow(
            contentPadding = PaddingValues(all = SmallPadding),
            modifier = Modifier
                .fillMaxWidth()
                .height(SectionSize),
        ) {
            items(items = sourceNews) { newsItem ->
                PopularNewsItem(newsItem = newsItem) { currentNewsItem ->
                    navController.currentBackStackEntry?.arguments?.putParcelable(NAV_NEWS_ITEM, currentNewsItem)
                    navController.navigate(NavigationScreens.NewsScreen.name)
                }
            }
        }
    }
}

@Composable
private fun BackButton(navController: NavController) {
    FloatingActionButton(
        modifier = Modifier
            .statusBarsPadding()
            .padding(all = HalfPadding)
            .size(size = SmallFabSize),
        shape = CircleShape,
        backgroundColor = White,
        onClick = { navController.navigateUp() },
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(Black)
        )
    }
}
