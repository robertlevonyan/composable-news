package com.robertlevonyan.composable.newsapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _searchInput = MutableStateFlow("")
    val searchInput: StateFlow<String> = _searchInput

    private val _searchResults = MutableStateFlow(emptyList<NewsItem>())
    val searchResults: StateFlow<List<NewsItem>> = _searchResults

    fun onSearchInput(input: String) {
        _searchInput.value = input
        viewModelScope.launch {
            delay(300)
            _searchResults.value = newsUseCase.search(input)
        }
    }
}
