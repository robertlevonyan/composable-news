package com.robertlevonyan.composable.newsapp.domain.repository

import com.robertlevonyan.composable.newsapp.data.entity.NewsResponse
import com.robertlevonyan.composable.newsapp.data.entity.SourceResponse
import com.robertlevonyan.composable.newsapp.data.entity.weather.WeatherResponse

interface NewsRepository {
    suspend fun getNews(
        category: String = "",
        query: String = "",
        sources: String = "",
        limit: Int,
        offset: Int,
    ): NewsResponse

    suspend fun search(
        query: String = "",
    ): NewsResponse

    suspend fun getSources(): SourceResponse

    suspend fun getWeather(): WeatherResponse
}
