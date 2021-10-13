package com.robertlevonyan.composable.newsapp.data.entity.error

sealed class NewsException: Exception() {
    object BreakingNewsError: NewsException()
    object PopularNewsError: NewsException()
    object SourcesError: NewsException()
    object InitialSourcesError: NewsException()
    object SourceNewsError: NewsException()
}

