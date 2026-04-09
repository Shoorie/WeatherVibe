@file:UseSerializers(LocalDateTimeIsoSerializer::class)

package com.weather.vibe.data.weather.local.cache

import com.weather.vibe.core.time.serializer.LocalDateTimeIsoSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDateTime

@Serializable
internal data class CachedHourlyWeather(
  val apparentTemperature: Double = 0.0,
  val cloudCover: Int = 0,
  val condition: String,
  val dewPoint: Double = 0.0,
  val humidity: Int,
  val precipitation: Double = 0.0,
  val precipitationProbability: Int,
  val surfacePressure: Double = 0.0,
  val temperature: Double,
  val time: LocalDateTime,
  val visibility: Double = 0.0,
  val windGusts: Double = 0.0,
  val windSpeed: Double
)
