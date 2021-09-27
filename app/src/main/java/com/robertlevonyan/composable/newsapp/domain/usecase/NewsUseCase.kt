package com.robertlevonyan.composable.newsapp.domain.usecase

import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem

interface NewsUseCase {
    suspend fun getBreakingNews(): List<NewsItem>

    suspend fun getPopularNews(category: Category): List<NewsItem>
}
