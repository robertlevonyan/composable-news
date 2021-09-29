package com.robertlevonyan.composable.newsapp.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.ui.screens.main.sections.BreakingNews.BreakingNewsSection
import com.robertlevonyan.composable.newsapp.ui.screens.main.sections.PopularNews.PopularNewsSection
import com.robertlevonyan.composable.newsapp.ui.screens.main.sections.Sources.SourcesSection
import com.robertlevonyan.composable.newsapp.ui.screens.main.sections.Weather.WeatherSection
import com.robertlevonyan.composable.newsapp.ui.theme.CornerRadius
import com.robertlevonyan.composable.newsapp.ui.theme.FabPadding
import com.robertlevonyan.composable.newsapp.ui.theme.LargeBottomPadding

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
fun MainScreenContent(mainViewModel: MainViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(state = ScrollState(0))
                .background(colorResource(id = R.color.background))
                .padding(bottom = LargeBottomPadding),
        ) {
            BreakingNewsSection(mainViewModel = mainViewModel)
            PopularNewsSection(mainViewModel = mainViewModel)
            SourcesSection(mainViewModel = mainViewModel)
            WeatherSection(mainViewModel = mainViewModel)
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(FabPadding),
            shape = RoundedCornerShape(size = CornerRadius),
            onClick = {

            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                alignment = Alignment.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
