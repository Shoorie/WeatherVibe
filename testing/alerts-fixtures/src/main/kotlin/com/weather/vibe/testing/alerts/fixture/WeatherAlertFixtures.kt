package com.weather.vibe.testing.alerts.fixture

import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.domain.alerts.model.WeatherAlert.HeavyRainImminent
import com.weather.vibe.domain.alerts.model.WeatherAlert.PoorAirQuality
import com.weather.vibe.domain.alerts.model.WeatherAlert.SharpTemperatureDrop
import com.weather.vibe.domain.alerts.model.WeatherAlert.ThunderstormImminent
import java.time.LocalDateTime

object WeatherAlertFixtures {

  val EXPECTED_AT: LocalDateTime = LocalDateTime.of(2026, 4, 16, 18, 0)
  const val HEAVY_RAIN_MM = 6.0
  const val TEMPERATURE_DROP_DEGREES = 9.0
  const val POOR_AIR_QUALITY_AQI = 75

  val THUNDERSTORM: ThunderstormImminent = thunderstorm()
  val HEAVY_RAIN: HeavyRainImminent = heavyRain()
  val TEMPERATURE_DROP: SharpTemperatureDrop = temperatureDrop()
  val POOR_AIR_QUALITY: PoorAirQuality = poorAirQuality()

  fun thunderstorm(
    expectedAt: LocalDateTime = EXPECTED_AT
  ): ThunderstormImminent =
    ThunderstormImminent(expectedAt = expectedAt)

  fun heavyRain(
    expectedAt: LocalDateTime = EXPECTED_AT,
    millimetres: Double = HEAVY_RAIN_MM
  ): HeavyRainImminent =
    HeavyRainImminent(expectedAt = expectedAt, millimetres = millimetres)

  fun temperatureDrop(
    expectedAt: LocalDateTime = EXPECTED_AT,
    degreesCelsius: Double = TEMPERATURE_DROP_DEGREES
  ): SharpTemperatureDrop =
    SharpTemperatureDrop(expectedAt = expectedAt, degreesCelsius = degreesCelsius)

  fun poorAirQuality(
    expectedAt: LocalDateTime = EXPECTED_AT,
    europeanAqi: Int = POOR_AIR_QUALITY_AQI,
    level: AqiLevel = POOR
  ): PoorAirQuality =
    PoorAirQuality(expectedAt = expectedAt, europeanAqi = europeanAqi, level = level)
}
