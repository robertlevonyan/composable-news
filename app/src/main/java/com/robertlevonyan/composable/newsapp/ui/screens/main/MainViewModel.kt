package com.robertlevonyan.composable.newsapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _breakingNews = MutableStateFlow(emptyList<NewsItem>())
    val breakingNews: StateFlow<List<NewsItem>> get() = _breakingNews

    private val _popularNews = MutableStateFlow(emptyList<NewsItem>())
    val popularNews: StateFlow<List<NewsItem>> get() = _popularNews

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

    fun getBreakingNews() {
        viewModelScope.launch {
            _breakingNews.value = newsUseCase.getBreakingNews()
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
            _popularNews.value = newsUseCase.getPopularNews(category = category)
        }
    }

    fun selectFirstCategory() {
        updateCategories(categories.value.first())
    }
}