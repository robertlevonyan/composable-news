package com.robertlevonyan.composable.newsapp.domain.usecase

import androidx.paging.PagingData
import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.data.entity.SourceItem
import com.robertlevonyan.composable.newsapp.data.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    suspend fun getBreakingNews(): ActionResult<List<NewsItem>>

    suspend fun getPopularNews(category: Category): ActionResult<List<NewsItem>>

    suspend fun getSources(): ActionResult<List<SourceItem>>

    suspend fun getInitialSources(): ActionResult<List<SourceItem>>

    suspend fun getSourceNews(newsItem: NewsItem): ActionResult<List<NewsItem>>

    fun search(input: String): Flow<PagingData<NewsItem>>

    fun getNewsBySource(sourceId: String): Flow<PagingData<NewsItem>>

    fun getWeather(): Flow<Weather>
}
