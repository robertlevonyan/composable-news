package com.robertlevonyan.composable.newsapp.domain.usecase

import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(private val newsRepository: NewsRepository) : NewsUseCase {
    override suspend fun getBreakingNews(): List<NewsItem> = withContext(Dispatchers.IO) {
        newsRepository.getNews(
            limit = 15,
            offset = 0,
        ).articles
    }

    override suspend fun getPopularNews(category: Category): List<NewsItem> = withContext(Dispatchers.IO) {
        newsRepository.getNews(
            category = category.name.lowercase(),
            limit = 15,
            offset = 0,
        ).articles
    }
}
