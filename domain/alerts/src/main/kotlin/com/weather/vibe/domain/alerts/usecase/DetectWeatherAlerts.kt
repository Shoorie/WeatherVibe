package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory

@Factory
class DetectWeatherAlerts internal constructor(
  private val detectHeavyRainAlert: DetectHeavyRainAlert,
  private val detectTemperatureDropAlert: DetectTemperatureDropAlert,
  private val detectThunderstormAlert: DetectThunderstormAlert,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(weather: WeatherData): List<WeatherAlert> {

    val window = weather.hourlyForecast.within(LOOKAHEAD_HOURS)
    if (window.isEmpty()) return emptyList()

    return listOfNotNull(
      detectThunderstormAlert(window),
      detectHeavyRainAlert(window),
      detectTemperatureDropAlert(window)
    )
  }

  private fun List<HourlyWeather>.within(hours: Long): List<HourlyWeather> {
    val now = timeProvider.now()
    val horizon = now.plusHours(hours)
    return filter { it.time in now..horizon }
  }

  private companion object {
    const val LOOKAHEAD_HOURS = 6L
  }
}
