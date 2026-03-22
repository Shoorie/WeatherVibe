package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(

  @SerialName("current_weather")
  val currentWeather: CurrentWeatherDto? = null,

  @SerialName("daily")
  val daily: DailyDataDto? = null,

  @SerialName("hourly")
  val hourly: HourlyDataDto? = null,

  @SerialName("latitude")
  val latitude: Double,

  @SerialName("longitude")
  val longitude: Double,

  @SerialName("timezone")
  val timezone: String,
)
