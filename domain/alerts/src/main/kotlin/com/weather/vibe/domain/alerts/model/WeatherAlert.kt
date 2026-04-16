package com.weather.vibe.domain.alerts.model

import java.time.LocalDateTime

sealed interface WeatherAlert {

  val expectedAt: LocalDateTime

  data class ThunderstormImminent(
    override val expectedAt: LocalDateTime
  ) : WeatherAlert

  data class HeavyRainImminent(
    override val expectedAt: LocalDateTime,
    val millimetres: Double
  ) : WeatherAlert

  data class SharpTemperatureDrop(
    override val expectedAt: LocalDateTime,
    val degreesCelsius: Double
  ) : WeatherAlert
}
