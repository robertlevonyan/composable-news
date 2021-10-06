package com.robertlevonyan.composable.newsapp.ui.screens.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.ui.components.SearchNewsItem
import com.robertlevonyan.composable.newsapp.ui.navigation.NAV_NEWS_ITEM
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.theme.Accent
import com.robertlevonyan.composable.newsapp.ui.theme.FabPadding
import com.robertlevonyan.composable.newsapp.ui.theme.HalfPadding
import com.robertlevonyan.composable.newsapp.ui.theme.Text50

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(
        content = {
            SearchScreenContent(navController = navController)
        }
    )
}

@Composable
private fun SearchScreenContent(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
) {
    val searchInput: String by searchViewModel.searchInput.collectAsState()
    val searchResults: LazyPagingItems<NewsItem> = searchViewModel.onSearchInput(searchInput).collectAsLazyPagingItems()

    SearchResults(
        searchViewModel = searchViewModel,
        searchInput = searchInput,
        searchResults = searchResults,
        navController = navController,
    )
}

@Composable
private fun SearchResults(
    searchViewModel: SearchViewModel,
    searchInput: String,
    searchResults: LazyPagingItems<NewsItem>,
    navController: NavController,
) {
    LazyColumn(
        modifier = Modifier
            .padding(all = HalfPadding)
            .fillMaxSize()
            .navigationBarsPadding(),
    ) {
        item {
            SearchBox(
                searchInput = searchInput,
                onSearchInputChange = searchViewModel::onSearchInput,
            )
        }
        items(count = searchResults.itemCount) { index ->
            searchResults[index]?.let { newsItem ->
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

@Composable
private fun SearchBox(searchInput: String, onSearchInputChange: (String) -> Unit) {
    TextField(
        value = searchInput,
        onValueChange = onSearchInputChange,
        placeholder = {
            Text(
                text = stringResource(id = R.string.hint_search),
                fontSize = Text50,
                fontFamily = FontFamily(Font(resId = R.font.newsreader_bold)),
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .statusBarsPadding()
            .padding(all = FabPadding),
        singleLine = true,
        shape = RoundedCornerShape(size = 0.dp),
        textStyle = TextStyle(
            fontSize = Text50,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resId = R.font.newsreader_bold)),
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Accent,
        ),
    )
}
