package com.robertlevonyan.composable.newsapp.data.entity.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("clouds")
    val clouds: WeatherClouds,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("main")
    val main: WeatherMain,
    @SerializedName("weather")
    val weather: List<WeatherData>,
    @SerializedName("wind")
    val wind: Wind?,
    @SerializedName("rain")
    val rain: Precipitation?,
    @SerializedName("snow")
    val snow: Precipitation?,
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("dt")
    val dt: Long,
)

data class WeatherClouds(
    @SerializedName("all")
    val all: Int,
)

data class WeatherMain(
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
)

data class WeatherData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)

data class Wind(
    @SerializedName("speed")
    val speed: Float?,
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("gust")
    val gust: Int?,
)

data class Precipitation(
    @SerializedName("1h")
    val oneH: Int?,
    @SerializedName("3h")
    val three3: Int?,
)

data class Sys(
    @SerializedName("sunrise")
    val sunrise: Long?,
    @SerializedName("sunset")
    val sunset: Long?,
)
