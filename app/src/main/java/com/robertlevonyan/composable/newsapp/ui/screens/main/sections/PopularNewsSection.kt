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
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.ui.components.Category
import com.robertlevonyan.composable.newsapp.ui.components.PopularNewsItem
import com.robertlevonyan.composable.newsapp.ui.components.SectionHeadingText
import com.robertlevonyan.composable.newsapp.ui.components.ShowLoading
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainViewModel
import com.robertlevonyan.composable.newsapp.ui.theme.HalfPadding
import com.robertlevonyan.composable.newsapp.ui.theme.SectionSize
import com.robertlevonyan.composable.newsapp.ui.theme.SmallPadding

object PopularNews {
    @Composable
    fun PopularNewsSection(mainViewModel: MainViewModel) {
        SectionHeadingText(text = stringResource(id = R.string.label_popular))

        val categories by mainViewModel.categories.collectAsState()
        val popularNews by mainViewModel.popularNews.collectAsState()
        val sectionHeight = SectionSize

        LazyRow(contentPadding = PaddingValues(all = HalfPadding)) {
            items(items = categories) { category ->
                ItemCategory(category, mainViewModel::updateCategories)
            }
        }

        if (popularNews.isEmpty()) {
            ShowLoading(sectionHeight = sectionHeight)
        } else {
            LazyRow(
                contentPadding = PaddingValues(all = SmallPadding),
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
            modifier = Modifier.padding(all = SmallPadding),
            selected = category.selected,
            onCategorySelected = onCategorySelected,
        )
    }
}
