package com.robertlevonyan.composable.newsapp.data.entity.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
  @SerialName("id")
  val id: Int,
  @SerialName("name")
  val name: String,
  @SerialName("clouds")
  val clouds: WeatherClouds,
  @SerialName("visibility")
  val visibility: Int,
  @SerialName("main")
  val main: WeatherMain,
  @SerialName("weather")
  val weather: List<WeatherData>,
  @SerialName("wind")
  val wind: Wind?,
  @SerialName("rain")
  val rain: Precipitation?,
  @SerialName("snow")
  val snow: Precipitation?,
  @SerialName("sys")
  val sys: Sys?,
  @SerialName("dt")
  val dt: Long,
)

@Serializable
data class WeatherClouds(
  @SerialName("all")
  val all: Int,
)

@Serializable
data class WeatherMain(
  @SerialName("temp")
  val temp: Float,
  @SerialName("feels_like")
  val feelsLike: Float,
  @SerialName("temp_min")
  val tempMin: Float,
  @SerialName("temp_max")
  val tempMax: Float,
  @SerialName("pressure")
  val pressure: Int,
  @SerialName("humidity")
  val humidity: Int,
)

@Serializable
data class WeatherData(
  @SerialName("id")
  val id: Int,
  @SerialName("main")
  val main: String,
  @SerialName("description")
  val description: String,
  @SerialName("icon")
  val icon: String,
)

@Serializable
data class Wind(
  @SerialName("speed")
  val speed: Float?,
  @SerialName("deg")
  val deg: Int?,
  @SerialName("gust")
  val gust: Int?,
)

@Serializable
data class Precipitation(
  @SerialName("1h")
  val oneH: Int?,
  @SerialName("3h")
  val three3: Int?,
)

@Serializable
data class Sys(
  @SerialName("sunrise")
  val sunrise: Long?,
  @SerialName("sunset")
  val sunset: Long?,
)
