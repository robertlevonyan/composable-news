package com.robertlevonyan.composable.newsapp.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.ui.components.GeneralText
import com.robertlevonyan.composable.newsapp.ui.components.ItemHeadingText
import com.robertlevonyan.composable.newsapp.ui.theme.*

@Composable
fun NewsScreen(newsViewModel: NewsViewModel = hiltViewModel(), navController: NavController, newsItemTitle: String?) {
    if (newsItemTitle == null) {
        navController.navigateUp()
        return
    }

    newsViewModel.getSingleNews(newsItemTitle)

    Scaffold(
        content = {
            NewsScreenContent(
                newsViewModel = newsViewModel,
                navController = navController,
            )
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsScreenContent(newsViewModel: NewsViewModel, navController: NavController) {
    val currentNews by newsViewModel.currentNews.collectAsState()
    val news = currentNews ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        Box {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = SectionSize),
                painter = rememberImagePainter(data = news.image),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            BackButton(navController = navController)
        }

        ItemHeadingText(
            text = news.title ?: "",
            textColor = if (isSystemInDarkTheme()) WhitePure else BlackPure,
        )

        GeneralText(
            text = news.content ?: "",
            textColor = if (isSystemInDarkTheme()) White else Black,
        )
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
