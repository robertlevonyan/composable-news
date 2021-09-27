package com.robertlevonyan.composable.newsapp.domain.repository

import com.robertlevonyan.composable.newsapp.data.entity.NewsResponse

interface NewsRepository {
    suspend fun getNews(
        category: String = "",
        limit: Int,
        offset: Int,
    ): NewsResponse
}
