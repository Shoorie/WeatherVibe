package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(

  @SerialName("current_weather")
  val currentWeather: CurrentWeatherDto? = null,

  val latitude: Double,
  val longitude: Double,
  val timezone: String,
  val hourly: HourlyDataDto? = null,
  val daily: DailyDataDto? = null
)
