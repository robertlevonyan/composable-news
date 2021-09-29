package com.robertlevonyan.composable.newsapp.domain.usecase

import com.robertlevonyan.composable.newsapp.data.entity.Category
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.data.entity.SourceItem
import com.robertlevonyan.composable.newsapp.data.entity.weather.Weather
import com.robertlevonyan.composable.newsapp.data.entity.weather.WeatherInfo
import com.robertlevonyan.composable.newsapp.data.entity.weather.WeatherResponse
import com.robertlevonyan.composable.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(private val newsRepository: NewsRepository) : NewsUseCase {
    override suspend fun getBreakingNews(): List<NewsItem> = withContext(Dispatchers.IO) {
        newsRepository.getNews(
            limit = 15,
            offset = 0,
        ).articles
    }

    override suspend fun getPopularNews(category: Category): List<NewsItem> = withContext(Dispatchers.IO) {
        newsRepository.getNews(
            category = category.name.lowercase(),
            limit = 15,
            offset = 0,
        ).articles
    }

    override suspend fun getSingleNews(title: String): NewsItem? = withContext(Dispatchers.IO) {
        newsRepository.getNews(
            query = title,
            limit = 1,
            offset = 0
        ).articles.firstOrNull()
    }

    override suspend fun getSources(): List<SourceItem> = withContext(Dispatchers.IO) {
        newsRepository.getSources().sources
    }

    override suspend fun getInitialSources(): List<SourceItem> = withContext(Dispatchers.IO) {
        getSources().subList(0, 12)
    }

    override fun getWeather(): Flow<Weather> = flow {
        while (true) {
            delay(timeMillis = 1000L)
            val weatherResponse = newsRepository.getWeather()
            val weather = Weather(
                name = weatherResponse.name,
                sunrise = weatherResponse.sys?.sunrise?.let { Date(it) },
                sunset = weatherResponse.sys?.sunset?.let { Date(it) },
                windSpeed = weatherResponse.wind?.speed ?: 0f,
                visibility = weatherResponse.visibility,
                temperature = weatherResponse.main.temp,
                feelsLike = weatherResponse.main.feelsLike,
                tempMax = weatherResponse.main.tempMax,
                tempMin = weatherResponse.main.tempMin,
                pressure = weatherResponse.main.pressure,
                humidity = weatherResponse.main.humidity,
                weatherInfo = weatherResponse.getWeatherInfo(),
            )
            emit(weather)
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
