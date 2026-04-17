package com.weather.vibe.testing.airquality.fixture

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.COORDINATES
import java.time.LocalDateTime

object AirQualityFixtures {

  const val GOOD_AQI = 15
  const val FAIR_AQI = 35
  const val MODERATE_AQI = 55
  const val POOR_AQI = 75
  const val VERY_POOR_AQI = 95
  const val EXTREMELY_POOR_AQI = 120
  val MEASURED_AT: LocalDateTime = LocalDateTime.of(2026, 4, 16, 15, 0)

  val GOOD: AirQuality = airQuality(europeanAqi = GOOD_AQI)
  val POOR: AirQuality = airQuality(europeanAqi = POOR_AQI)

  fun airQuality(
    coordinates: Coordinates = COORDINATES,
    europeanAqi: Int = POOR_AQI,
    measuredAt: LocalDateTime = MEASURED_AT
  ): AirQuality = AirQuality(
    coordinates = coordinates,
    europeanAqi = europeanAqi,
    measuredAt = measuredAt
  )
}
