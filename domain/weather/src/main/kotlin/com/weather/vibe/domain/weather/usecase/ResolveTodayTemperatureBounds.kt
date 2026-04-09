package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.TemperatureBounds
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory

@Factory
class ResolveTodayTemperatureBounds {

  operator fun invoke(data: WeatherData): TemperatureBounds {
    val today = data.dailyForecast.firstOrNull() ?: return fallback(data)
    return TemperatureBounds(
      min = today.minTemperature,
      max = today.maxTemperature
    )
  }

  private fun fallback(data: WeatherData): TemperatureBounds =
    TemperatureBounds(
      min = data.currentTemperature,
      max = data.currentTemperature
    )
}
