package com.weather.vibe.domain.alerts.model

import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.PollenSpecies
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

  data class PoorAirQuality(
    override val expectedAt: LocalDateTime,
    val europeanAqi: Int,
    val level: AqiLevel
  ) : WeatherAlert

  data class HighPollen(
    override val expectedAt: LocalDateTime,
    val species: List<PollenSpecies>
  ) : WeatherAlert
}
