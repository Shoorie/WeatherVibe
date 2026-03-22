package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(

  @SerialName("is_day")
  val isDay: Int,

  @SerialName("temperature")
  val temperature: Double,

  @SerialName("time")
  val time: String,

  @SerialName("weathercode")
  val weathercode: Int,

  @SerialName("winddirection")
  val winddirection: Double,

  @SerialName("windspeed")
  val windspeed: Double,
)
