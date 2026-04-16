package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.model.WeatherAlert.HeavyRainImminent
import com.weather.vibe.domain.weather.model.HourlyWeather
import org.koin.core.annotation.Factory

@Factory
class DetectHeavyRainAlert internal constructor() {

  operator fun invoke(forecast: List<HourlyWeather>): HeavyRainImminent? =
    forecast
      .firstOrNull { it.precipitation >= HEAVY_RAIN_MM_PER_HOUR }
      ?.let {
        HeavyRainImminent(
          expectedAt = it.time,
          millimetres = it.precipitation
        )
      }

  private companion object {
    const val HEAVY_RAIN_MM_PER_HOUR = 5.0
  }
}
