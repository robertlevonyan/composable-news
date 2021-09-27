package com.robertlevonyan.composable.newsapp.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.ui.components.*

@Composable
fun MainScreen() {
    Scaffold(
        content = {
            MainScreenContent()
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreenContent(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = ScrollState(0))
            .background(colorResource(id = R.color.background))
    ) {
        mainViewModel.getBreakingNews()
        mainViewModel.selectFirstCategory()

        BreakingNewsSection(mainViewModel = mainViewModel)
        PopularNewsSection(mainViewModel = mainViewModel)
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun BreakingNewsSection(mainViewModel: MainViewModel) {
    SectionHeadingText(text = stringResource(id = R.string.label_breaking))

    val news by mainViewModel.breakingNews.collectAsState(initial = mainViewModel.breakingNews.value)
    val sectionHeight = 250.dp

    if (news.isEmpty()) {
        ShowLoading(sectionHeight = sectionHeight)
    } else {
        val pagerState = rememberSaveable(saver = PagerState.Saver) {
            PagerState(pageCount = news.size)
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(sectionHeight)
        ) { page ->
            BreakingNewsItem(newsItem = news[page])
        }
    }
}

@Composable
fun PopularNewsSection(mainViewModel: MainViewModel) {
    SectionHeadingText(text = stringResource(id = R.string.label_popular))

    val categories by mainViewModel.categories.collectAsState(initial = mainViewModel.categories.value)
    val popularNews by mainViewModel.popularNews.collectAsState(initial = mainViewModel.popularNews.value)
    val sectionHeight = 250.dp

    LazyRow(contentPadding = PaddingValues(8.dp)) {
        items(items = categories) { category ->
            ItemCategory(category, mainViewModel::updateCategories)
        }
    }

    if (popularNews.isEmpty()) {
        ShowLoading(sectionHeight = sectionHeight)
    } else {
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(sectionHeight),
        ) {
            items(items = popularNews) { newsItem ->
                PopularNewsItem(newsItem = newsItem)
            }
        }
    }
}

@Composable
private fun ItemCategory(
    category: Category,
    onCategorySelected: (Category) -> Unit,
) {
    Category(
        text = category.name.replaceFirstChar { it.uppercase() },
        modifier = Modifier.padding(4.dp),
        selected = category.selected,
        onCategorySelected = onCategorySelected,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
