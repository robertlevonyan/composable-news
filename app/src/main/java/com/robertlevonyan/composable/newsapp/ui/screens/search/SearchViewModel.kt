package com.robertlevonyan.composable.newsapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _searchInput = MutableStateFlow("")
    val searchInput: StateFlow<String> = _searchInput

    fun onSearchInput(input: String): Flow<PagingData<NewsItem>> {
        _searchInput.value = input
        return newsUseCase.search(input)
    }
}
