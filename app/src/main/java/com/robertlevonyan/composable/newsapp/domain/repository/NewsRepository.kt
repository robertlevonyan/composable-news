package com.robertlevonyan.composable.newsapp.domain.repository

import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
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
    ): ActionResult<NewsResponse>

    suspend fun search(
        query: String = "",
        page: Int,
    ): ActionResult<NewsResponse>

    suspend fun getSources(): ActionResult<SourceResponse>

    suspend fun getWeather(): ActionResult<WeatherResponse>
}
