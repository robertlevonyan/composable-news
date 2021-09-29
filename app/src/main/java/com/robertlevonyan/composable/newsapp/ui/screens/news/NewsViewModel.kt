package com.robertlevonyan.composable.newsapp.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _currentNews = MutableStateFlow<NewsItem?>(null)
    val currentNews: StateFlow<NewsItem?> get() = _currentNews

    fun getSingleNews(title: String) {
        viewModelScope.launch {
            _currentNews.value = newsUseCase.getSingleNews(title = title)
        }
    }
}