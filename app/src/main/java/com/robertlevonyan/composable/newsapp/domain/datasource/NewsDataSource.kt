package com.robertlevonyan.composable.newsapp.domain.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.domain.repository.NewsRepository

class NewsDataSource(private val query: String, private val newsRepository: NewsRepository) :
    PagingSource<Int, NewsItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val nextPage = params.key ?: 1
        if (query.isEmpty()) return LoadResult.Error(IllegalArgumentException("query cannot be empty"))

        return newsRepository.search(query = query, page = nextPage).let { result ->
            when (result) {
                is ActionResult.Success -> LoadResult.Page(
                    data = result.data.articles,
                    prevKey =
                    if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage.plus(1),
                )
                is ActionResult.Error -> LoadResult.Error(result.error)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? = null
}
