package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.repository.WeatherRepository
import org.koin.core.annotation.Factory

@Factory
class GetCurrentTemperature(
  private val repository: WeatherRepository
) {

  suspend operator fun invoke(latitude: Double, longitude: Double): Double =
    repository.getCurrentTemperature(latitude, longitude)
}
