package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TemperatureRange
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherKey
import org.koin.core.annotation.Factory

@Factory
class ComputeWeatherKey {

  operator fun invoke(
    condition: WeatherCondition,
    hour: Int,
    temperatureCelsius: Double
  ): WeatherKey =
    WeatherKey(
      condition = SimplifiedCondition.from(condition = condition),
      temperature = TemperatureRange.from(celsius = temperatureCelsius),
      timeOfDay = TimeOfDay.from(hour = hour)
    )
}
