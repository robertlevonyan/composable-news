package com.robertlevonyan.composable.newsapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.data.entity.SourceItem
import com.robertlevonyan.composable.newsapp.data.entity.weather.Weather
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _breakingNews = MutableStateFlow(emptyList<NewsItem>())
    val breakingNews: StateFlow<List<NewsItem>> get() = _breakingNews

    private val _breakingNewsError = MutableStateFlow(false)
    val breakingNewsError: StateFlow<Boolean> get() = _breakingNewsError

    private val _popularNews = MutableStateFlow(emptyList<NewsItem>())
    val popularNews: StateFlow<List<NewsItem>> get() = _popularNews

    private val _popularNewsError = MutableStateFlow(false)
    val popularNewsError: StateFlow<Boolean> get() = _popularNewsError

    private val _sources = MutableStateFlow(emptyList<SourceItem>())
    val sources: StateFlow<List<SourceItem>> get() = _sources

    private val _sourcesError = MutableStateFlow(false)
    val sourcesError: StateFlow<Boolean> get() = _sourcesError

    private val _areSourcesLoading = MutableStateFlow(false)
    val areSourcesLoading: StateFlow<Boolean> get() = _areSourcesLoading

    private val _areAllSources = MutableStateFlow(false)
    val areAllSources: StateFlow<Boolean> get() = _areAllSources

    private val _weather = newsUseCase.getWeather().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = Weather.EMPTY,
    )
    val weather: StateFlow<Weather> get() = _weather

    private val _categories = MutableStateFlow(
        listOf(
            Category(name = "business", selected = true),
            Category(name = "entertainment", selected = false),
            Category(name = "general", selected = false),
            Category(name = "health", selected = false),
            Category(name = "science", selected = false),
            Category(name = "sports", selected = false),
            Category(name = "technology", selected = false),
        )
    )
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        getBreakingNews()
        selectFirstCategory()
        getInitialSources()
    }

    private fun getBreakingNews() {
        viewModelScope.launch {
            newsUseCase.getBreakingNews().let { result ->
                when (result) {
                    is ActionResult.Success -> _breakingNews.value = result.data
                    is ActionResult.Error -> _breakingNewsError.value = true
                }
            }
        }
    }

    fun updateCategories(category: Category) {
        val updatedCategories = mutableListOf<Category>()
        val existingCategories = categories.value

        existingCategories.forEach {
            val updatedCategory = if (it.name == category.name) {
                category.copy(selected = true)
            } else {
                it.copy(selected = false)
            }
            updatedCategories.add(element = updatedCategory)
        }
        _categories.value = updatedCategories
        getPopularNews(category = category)
    }

    private fun getPopularNews(category: Category) {
        viewModelScope.launch {
            newsUseCase.getPopularNews(category = category).let { result ->
                when (result) {
                    is ActionResult.Success -> _popularNews.value = result.data
                    is ActionResult.Error -> _popularNewsError.value = true
                }
            }
        }
    }

    private fun selectFirstCategory() {
        updateCategories(categories.value.first())
    }

    fun getSources() {
        viewModelScope.launch {
            _areSourcesLoading.value = true
            newsUseCase.getSources().let { result ->
                when (result) {
                    is ActionResult.Success -> _sources.value = result.data
                    is ActionResult.Error -> _sourcesError.value = true
                }
            }
            _areAllSources.value = true
            _areSourcesLoading.value = false
        }
    }

    fun getInitialSources() {
        viewModelScope.launch {
            _areSourcesLoading.value = true
            newsUseCase.getInitialSources().let { result ->
                when (result) {
                    is ActionResult.Success -> _sources.value = result.data
                    is ActionResult.Error -> _sourcesError.value = true
                }
            }
            _areAllSources.value = false
            _areSourcesLoading.value = false
        }
    }
}
