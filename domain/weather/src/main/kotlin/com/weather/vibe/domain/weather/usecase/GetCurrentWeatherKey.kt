package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import org.koin.core.annotation.Factory

@Factory
class GetCurrentWeatherKey(
  private val computeWeatherKey: ComputeWeatherKey,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(data: WeatherData): WeatherKey =
    computeWeatherKey(
      condition = data.condition,
      hour = timeProvider.now().hour,
      temperatureCelsius = data.currentTemperature
    )
}
