package com.weather.vibe.domain.location.model

import com.weather.vibe.domain.weather.model.SimplifiedCondition
import java.time.Instant

data class LocationWeatherSnapshot(
  val condition: SimplifiedCondition,
  val feelsLikeC: Double,
  val highC: Double,
  val hourlyTemperaturesC: List<Double>,
  val humidityPercent: Int,
  val isDay: Boolean,
  val locationId: Long,
  val lowC: Double,
  val precipitationChancePercent: Int,
  val temperatureC: Double,
  val updatedAt: Instant,
  val windKph: Double
)
