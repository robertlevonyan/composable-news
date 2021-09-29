package com.robertlevonyan.composable.newsapp.data.entity.weather

import com.robertlevonyan.composable.newsapp.R
import java.util.*

data class Weather(
    val name: String,
    val sunrise: Date?,
    val sunset: Date?,
    val windSpeed: Float,
    val visibility: Int,
    val temperature: Float,
    val feelsLike: Float,
    val tempMin: Float,
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
    val weatherInfo: List<WeatherInfo>,
) {
    companion object {
        val EMPTY = Weather(
            name = "",
            sunrise = null,
            sunset = null,
            windSpeed = 0f,
            visibility = 0,
            temperature = 0f,
            feelsLike = 0f,
            tempMin = 0f,
            tempMax = 0f,
            pressure = 0,
            humidity = 0,
            weatherInfo = emptyList(),
        )
    }
}

sealed class WeatherInfo(val description: Int, val icon: String) {
    object Thunderstorm : WeatherInfo(R.string.weather_thunderstorm, "weather/thunderstorm.json")
    object Drizzle : WeatherInfo(R.string.weather_shower_rain, "weather/drizzle.json")
    object Rain : WeatherInfo(R.string.weather_rain, "weather/rain.json")
    object Snow : WeatherInfo(R.string.weather_snow, "weather/snow.json")
    object Mist : WeatherInfo(R.string.weather_mist, "weather/mist.json")
    object ClearSky : WeatherInfo(R.string.weather_clear_sky, "weather/sunny.json")
    object FewClouds : WeatherInfo(R.string.weather_few_clouds, "weather/cloudy.json")
    object ScatteredClouds : WeatherInfo(R.string.weather_scattered_clouds, "weather/overcast.json")
    object BrokenClouds : WeatherInfo(R.string.weather_broken_clouds, "weather/overcast.json")
}
