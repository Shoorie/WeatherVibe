package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(

  @SerialName("is_day") val isDay: Int,
  val time: String,

  val temperature: Double,
  val windspeed: Double,
  val winddirection: Double,
  val weathercode: Int,
)
