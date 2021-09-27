package com.robertlevonyan.composable.newsapp.data.repository

import com.robertlevonyan.composable.newsapp.BuildConfig
import com.robertlevonyan.composable.newsapp.data.entity.NewsResponse
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
            "apiKey" to BuildConfig.API_KEY,
            "limit" to limit,
            "offset" to offset,
            "country" to "us",
        ).apply {
            if (category.isNotEmpty()) {
                put("category", category)
            }
        }
    )
}
