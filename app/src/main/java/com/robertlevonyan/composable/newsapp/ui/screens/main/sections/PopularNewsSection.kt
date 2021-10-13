package com.robertlevonyan.composable.newsapp.ui.screens.main.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.ui.components.*
import com.robertlevonyan.composable.newsapp.ui.navigation.NAV_NEWS_ITEM
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainViewModel
import com.robertlevonyan.composable.newsapp.ui.theme.HalfPadding
import com.robertlevonyan.composable.newsapp.ui.theme.SectionSize
import com.robertlevonyan.composable.newsapp.ui.theme.SmallPadding

object PopularNews {
    @Composable
    fun PopularNewsSection(navController: NavController, mainViewModel: MainViewModel) {
        SectionHeadingText(text = stringResource(id = R.string.label_popular))

        val categories by mainViewModel.categories.collectAsState()
        val popularNews by mainViewModel.popularNews.collectAsState()
        val popularNewsError by mainViewModel.popularNewsError.collectAsState()

        LazyRow(contentPadding = PaddingValues(all = HalfPadding)) {
            items(items = categories) { category ->
                ItemCategory(category, mainViewModel::updateCategories)
            }
        }

        if (popularNews.isEmpty() && !popularNewsError) {
            ShowLoading(sectionHeight = SectionSize)
        } else if (popularNewsError) {
            LoadErrorPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SectionSize),
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(all = SmallPadding),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SectionSize),
            ) {
                items(
                    items = popularNews,
                    key = { it.title ?: "" },
                ) { newsItem ->
                    PopularNewsItem(newsItem = newsItem) { currentNewsItem ->
                        navController.currentBackStackEntry?.arguments?.putParcelable(NAV_NEWS_ITEM, currentNewsItem)
                        navController.navigate(NavigationScreens.NewsScreen.name)
                    }
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
            modifier = Modifier.padding(all = SmallPadding),
            selected = category.selected,
            onCategorySelected = onCategorySelected,
        )
    }
}
