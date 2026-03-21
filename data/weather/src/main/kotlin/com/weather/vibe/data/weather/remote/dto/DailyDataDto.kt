package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyDataDto(

  @SerialName("precipitation_probability_max")
  val precipitationProbabilityMax: List<Int> = emptyList(),

  @SerialName("precipitation_sum")
  val precipitationSum: List<Double> = emptyList(),

  @SerialName("temperature_2m_max")
  val temperature2mMax: List<Double> = emptyList(),

  @SerialName("temperature_2m_min")
  val temperature2mMin: List<Double> = emptyList(),

  @SerialName("uv_index_max")
  val uvIndexMax: List<Double> = emptyList(),

  @SerialName("windgusts_10m_max")
  val windgusts10mMax: List<Double> = emptyList(),

  @SerialName("windspeed_10m_max")
  val windspeed10mMax: List<Double> = emptyList(),

  val sunrise: List<String> = emptyList(),
  val sunset: List<String> = emptyList(),
  val time: List<String> = emptyList(),
  val weathercode: List<Int> = emptyList(),
)
