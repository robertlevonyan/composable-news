package com.robertlevonyan.composable.newsapp.ui.screens.main.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.robertlevonyan.chip.compose.ChipInteraction
import com.robertlevonyan.chip.compose.MaterialChipGroup
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.ui.components.LoadErrorPlaceholder
import com.robertlevonyan.composable.newsapp.ui.components.PopularNewsItem
import com.robertlevonyan.composable.newsapp.ui.components.SectionHeadingText
import com.robertlevonyan.composable.newsapp.ui.components.ShowLoading
import com.robertlevonyan.composable.newsapp.ui.navigation.NAV_NEWS_ITEM
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.theme.*

object PopularNews {
    @Composable
    fun PopularNewsSection(
        navController: NavController,
        categories: List<Category>,
        popularNews: List<NewsItem>,
        popularNewsError: Boolean,
        updateCategories: (Category) -> Unit,
    ) {
        SectionHeadingText(text = stringResource(id = R.string.label_popular))

        MaterialChipGroup(
            contentPadding = PaddingValues(all = HalfPadding),
            items = categories,
            createTitle = { category -> category.name.replaceFirstChar { it.uppercase() } },
            strokeColor = Accent,
            strokeSize = StrokeSize,
            backgroundColor = Color.Transparent,
            selectedBackgroundColor = Accent,
            textColor = colorResource(id = R.color.onSecondary),
            selectedTextColor = Black,
            fontFamily = FontFamily(Font(resId = R.font.newsreader_regular)),
            interaction = ChipInteraction.SelectableWithoutIcon,
            initialSelectedElementIndex = 0,
            onItemSelected = { _, category -> updateCategories.invoke(category) },
        )

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
}
