package com.robertlevonyan.composable.newsapp.data.repository

import com.robertlevonyan.composable.newsapp.BuildConfig
import com.robertlevonyan.composable.newsapp.data.entity.NewsResponse
import com.robertlevonyan.composable.newsapp.data.entity.SourceResponse
import com.robertlevonyan.composable.newsapp.data.entity.weather.WeatherResponse
import com.robertlevonyan.composable.newsapp.data.remote.ApiService
import com.robertlevonyan.composable.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsRepository {
    override suspend fun getNews(
        category: String,
        limit: Int,
        offset: Int,
    ): NewsResponse = apiService.get(
        url = "https://newsapi.org/v2/top-headlines",
        parameters = mutableMapOf(
            "apiKey" to BuildConfig.NEWS_KEY,
            "limit" to limit,
            "offset" to offset,
            "country" to "us",
        ).apply {
            if (category.isNotEmpty()) {
                put("category", category)
            }
        },
    )

    override suspend fun getSources(): SourceResponse = apiService.get(
        url = "https://newsapi.org/v2/top-headlines/sources",
        parameters = mapOf("apiKey" to BuildConfig.NEWS_KEY),
    )


    override suspend fun getWeather(): WeatherResponse = apiService.get(
        url = "https://api.openweathermap.org/data/2.5/weather",
        parameters = mapOf(
            "appid" to BuildConfig.WEATHER_KEY,
            "q" to "yerevan",
            "units" to "metric",
        )
    )
}
