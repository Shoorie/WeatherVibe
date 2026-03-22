package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDataDto(

  @SerialName("apparent_temperature")
  val apparentTemperature: List<Double> = emptyList(),

  @SerialName("cloudcover")
  val cloudcover: List<Int> = emptyList(),

  @SerialName("dewpoint_2m")
  val dewpoint2m: List<Double> = emptyList(),

  @SerialName("precipitation")
  val precipitation: List<Double> = emptyList(),

  @SerialName("precipitation_probability")
  val precipitationProbability: List<Int> = emptyList(),

  @SerialName("relative_humidity_2m")
  val relativeHumidity2m: List<Int> = emptyList(),

  @SerialName("surface_pressure")
  val surfacePressure: List<Double> = emptyList(),

  @SerialName("temperature_2m")
  val temperature2m: List<Double> = emptyList(),

  @SerialName("time")
  val time: List<String> = emptyList(),

  @SerialName("visibility")
  val visibility: List<Double> = emptyList(),

  @SerialName("weathercode")
  val weathercode: List<Int> = emptyList(),

  @SerialName("windgusts_10m")
  val windgusts10m: List<Double> = emptyList(),

  @SerialName("windspeed_10m")
  val windspeed10m: List<Double> = emptyList(),
)
