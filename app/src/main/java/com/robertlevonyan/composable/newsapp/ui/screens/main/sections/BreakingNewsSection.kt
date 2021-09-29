package com.robertlevonyan.composable.newsapp.ui.screens.main.sections

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.ui.components.BreakingNewsItem
import com.robertlevonyan.composable.newsapp.ui.components.SectionHeadingText
import com.robertlevonyan.composable.newsapp.ui.components.ShowLoading
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainViewModel
import com.robertlevonyan.composable.newsapp.ui.theme.SectionSize

object BreakingNews {
    @Composable
    @OptIn(ExperimentalPagerApi::class)
    fun BreakingNewsSection(navController: NavController, mainViewModel: MainViewModel) {
        SectionHeadingText(
            text = stringResource(id = R.string.label_breaking),
            modifier = Modifier.statusBarsPadding(),
        )

        val news by mainViewModel.breakingNews.collectAsState()

        if (news.isEmpty()) {
            ShowLoading(sectionHeight = SectionSize)
        } else {
            val pagerState = rememberSaveable(saver = PagerState.Saver) {
                PagerState(pageCount = news.size)
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.height(SectionSize)
            ) { page ->
                BreakingNewsItem(newsItem = news[page]) { newsItem ->
                    navController.navigate("${NavigationScreens.NewsScreen.name}/${newsItem.title}")
                }
            }
        }
    }
}
