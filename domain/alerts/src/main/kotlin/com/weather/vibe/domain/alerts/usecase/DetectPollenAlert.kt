package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.Pollen
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.alerts.model.WeatherAlert.HighPollen
import org.koin.core.annotation.Factory

@Factory
internal class DetectPollenAlert {

  operator fun invoke(pollen: Pollen): HighPollen? {

    val raised = pollen.readings
      .filter { it.level.ordinal >= ALERT_THRESHOLD.ordinal }
      .map { it.species }

    if (raised.isEmpty()) return null

    return HighPollen(
      expectedAt = pollen.measuredAt,
      species = raised
    )
  }

  private companion object {
    val ALERT_THRESHOLD: PollenLevel = HIGH
  }
}
