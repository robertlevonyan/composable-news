package com.robertlevonyan.composable.newsapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.data.entity.SourceItem
import com.robertlevonyan.composable.newsapp.data.entity.error.NewsException
import com.robertlevonyan.composable.newsapp.data.entity.weather.Weather
import com.robertlevonyan.composable.newsapp.data.entity.weather.WeatherInfo
import com.robertlevonyan.composable.newsapp.data.entity.weather.WeatherResponse
import com.robertlevonyan.composable.newsapp.domain.datasource.NewsDataSource
import com.robertlevonyan.composable.newsapp.domain.datasource.SourceNewsDataSource
import com.robertlevonyan.composable.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(private val newsRepository: NewsRepository) : NewsUseCase {
    override suspend fun getBreakingNews(): ActionResult<List<NewsItem>> = withContext(Dispatchers.IO) {
        newsRepository.getNews(limit = 15, offset = 0).let { response ->
            when (response) {
                is ActionResult.Success -> ActionResult.Success(response.data.articles)
                is ActionResult.Error -> ActionResult.Error(NewsException.BreakingNewsError)
            }
        }
    }

    override suspend fun getPopularNews(category: Category): ActionResult<List<NewsItem>> = withContext(Dispatchers.IO) {
        newsRepository.getNews(category = category.name.lowercase(), limit = 15, offset = 0).let { response ->
            when (response) {
                is ActionResult.Success -> ActionResult.Success(response.data.articles)
                is ActionResult.Error -> ActionResult.Error(NewsException.PopularNewsError)
            }
        }
    }

    override suspend fun getSources(): ActionResult<List<SourceItem>> = withContext(Dispatchers.IO) {
        newsRepository.getSources().let { response ->
            when (response) {
                is ActionResult.Success -> ActionResult.Success(response.data.sources)
                is ActionResult.Error -> ActionResult.Error(NewsException.SourcesError)
            }
        }
    }

    override suspend fun getInitialSources(): ActionResult<List<SourceItem>> = withContext(Dispatchers.IO) {
        getSources().let { result ->
            when (result) {
                is ActionResult.Success -> result.data
                    .subList(fromIndex = 0, toIndex = 12)
                    .let { ActionResult.Success(it) }
                is ActionResult.Error -> ActionResult.Error(NewsException.InitialSourcesError)
            }
        }
    }

    override suspend fun getSourceNews(newsItem: NewsItem): ActionResult<List<NewsItem>> = withContext(Dispatchers.IO) {
        newsRepository.getNews(sources = newsItem.source.id ?: "", limit = 10, offset = 0).let { response ->
            when (response) {
                is ActionResult.Success -> response.data.articles
                    .filter { sourceNewsItem -> sourceNewsItem.title != newsItem.title }
                    .let { ActionResult.Success(it) }
                is ActionResult.Error -> ActionResult.Error(NewsException.SourceNewsError)
            }
        }
    }

    override fun search(input: String): Flow<PagingData<NewsItem>> = Pager(PagingConfig(100)) {
        NewsDataSource(query = input, newsRepository = newsRepository)
    }.flow

    override fun getNewsBySource(sourceId: String): Flow<PagingData<NewsItem>> = Pager(PagingConfig(100)) {
        SourceNewsDataSource(sourceId = sourceId, newsRepository = newsRepository)
    }.flow

    override fun getWeather(): Flow<Weather> = flow {
        while (true) {
            delay(timeMillis = 1000L)
            when (val weatherResponse = newsRepository.getWeather()) {
                is ActionResult.Success -> {
                    val weather = Weather(
                        name = weatherResponse.data.name,
                        sunrise = weatherResponse.data.sys?.sunrise?.let { Date(it) },
                        sunset = weatherResponse.data.sys?.sunset?.let { Date(it) },
                        windSpeed = weatherResponse.data.wind?.speed ?: 0f,
                        visibility = weatherResponse.data.visibility,
                        temperature = weatherResponse.data.main.temp,
                        feelsLike = weatherResponse.data.main.feelsLike,
                        tempMax = weatherResponse.data.main.tempMax,
                        tempMin = weatherResponse.data.main.tempMin,
                        pressure = weatherResponse.data.main.pressure,
                        humidity = weatherResponse.data.main.humidity,
                        weatherInfo = weatherResponse.data.getWeatherInfo(),
                    )
                    emit(weather)
                }
                is ActionResult.Error -> error("No weather data")
            }
        }
    }
}

private fun WeatherResponse.getWeatherInfo(): List<WeatherInfo> = weather.map { weather ->
    when (weather.id) {
        in 200..299 -> WeatherInfo.Thunderstorm
        in 300..399 -> WeatherInfo.Drizzle
        in 500..599 -> WeatherInfo.Rain
        in 600..699 -> WeatherInfo.Snow
        in 700..799 -> WeatherInfo.Mist
        800 -> WeatherInfo.ClearSky
        801 -> WeatherInfo.FewClouds
        802 -> WeatherInfo.ScatteredClouds
        803, 804 -> WeatherInfo.BrokenClouds
        else -> WeatherInfo.ClearSky
    }
}
