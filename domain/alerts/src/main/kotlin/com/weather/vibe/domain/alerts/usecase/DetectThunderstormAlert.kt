package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.model.WeatherAlert.ThunderstormImminent
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.THUNDERSTORM
import org.koin.core.annotation.Factory

@Factory
class DetectThunderstormAlert internal constructor() {

  operator fun invoke(forecast: List<HourlyWeather>): ThunderstormImminent? =
    forecast
      .firstOrNull { it.condition == THUNDERSTORM }
      ?.let { ThunderstormImminent(expectedAt = it.time) }
}
