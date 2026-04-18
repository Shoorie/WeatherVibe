package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.domain.alerts.model.WeatherAlert.PoorAirQuality
import org.koin.core.annotation.Factory

@Factory
class DetectAqiAlert {

  operator fun invoke(airQuality: AirQuality): PoorAirQuality? {
    if (airQuality.level.ordinal < ALERT_THRESHOLD.ordinal) return null
    return PoorAirQuality(
      expectedAt = airQuality.measuredAt,
      europeanAqi = airQuality.europeanAqi,
      level = airQuality.level
    )
  }

  private companion object {
    val ALERT_THRESHOLD: AqiLevel = POOR
  }
}
