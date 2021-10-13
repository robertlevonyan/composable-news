package com.robertlevonyan.composable.newsapp.ui.screens.sources

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    fun getNewsBySource(sourceId: String): Flow<PagingData<NewsItem>> =
        newsUseCase.getNewsBySource(sourceId = sourceId)
}
