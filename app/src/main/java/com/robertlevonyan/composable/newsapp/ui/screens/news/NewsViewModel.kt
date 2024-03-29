package com.robertlevonyan.composable.newsapp.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _sourceNews = MutableStateFlow(emptyList<NewsItem>())
    val sourceNews: StateFlow<List<NewsItem>> get() = _sourceNews

    private val _sourceNewsError = MutableStateFlow(false)
    val sourceNewsError: StateFlow<Boolean> get() = _sourceNewsError

    fun getSourceNews(newsItem: NewsItem) {
        viewModelScope.launch {
            newsUseCase.getSourceNews(newsItem).let { result ->
                when (result) {
                    is ActionResult.Success -> _sourceNews.value = result.data
                    is ActionResult.Error -> _sourceNewsError.value = true
                }
            }
        }
    }
}
