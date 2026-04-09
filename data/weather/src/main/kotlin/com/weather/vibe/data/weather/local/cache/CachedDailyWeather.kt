@file:UseSerializers(
  LocalDateIsoSerializer::class,
  LocalDateTimeIsoSerializer::class
)

package com.weather.vibe.data.weather.local.cache

import com.weather.vibe.core.time.serializer.LocalDateIsoSerializer
import com.weather.vibe.core.time.serializer.LocalDateTimeIsoSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
internal data class CachedDailyWeather(
  val condition: String,
  val date: LocalDate,
  val maxTemperature: Double,
  val minTemperature: Double,
  val precipitationProbability: Int,
  val precipitationSum: Double = 0.0,
  val sunrise: LocalDateTime? = null,
  val sunset: LocalDateTime? = null,
  val uvIndexMax: Double = 0.0,
  val windGustsMax: Double = 0.0,
  val windSpeedMax: Double = 0.0
)
