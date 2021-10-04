package com.robertlevonyan.composable.newsapp.domain.usecase

import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.data.entity.SourceItem
import com.robertlevonyan.composable.newsapp.data.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    suspend fun getBreakingNews(): List<NewsItem>

    suspend fun getPopularNews(category: Category): List<NewsItem>

    suspend fun getSources(): List<SourceItem>

    suspend fun getInitialSources(): List<SourceItem>

    suspend fun getSourceNews(newsItem: NewsItem): List<NewsItem>

    suspend fun search(input: String): List<NewsItem>

    fun getWeather(): Flow<Weather>
}
