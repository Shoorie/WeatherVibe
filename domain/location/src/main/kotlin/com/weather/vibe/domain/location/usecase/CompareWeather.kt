package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.model.WeatherAdvantage
import com.weather.vibe.domain.location.model.WeatherComparison
import org.koin.core.annotation.Factory

@Factory
class CompareWeather {

  operator fun invoke(
    first: LocationWeatherSnapshot,
    second: LocationWeatherSnapshot
  ): WeatherComparison = WeatherComparison(
    temperature = higherWins(first = first.temperatureC, second = second.temperatureC),
    wind = lowerWins(first = first.windKph, second = second.windKph),
    humidity = lowerWins(first = first.humidityPercent, second = second.humidityPercent),
    rain = lowerWins(
      first = first.precipitationChancePercent,
      second = second.precipitationChancePercent
    )
  )

  private fun higherWins(first: Double, second: Double): WeatherAdvantage = when {
    first > second -> WeatherAdvantage.FirstLocation
    second > first -> WeatherAdvantage.SecondLocation
    else -> WeatherAdvantage.Neither
  }

  private fun lowerWins(first: Double, second: Double): WeatherAdvantage = when {
    first < second -> WeatherAdvantage.FirstLocation
    second < first -> WeatherAdvantage.SecondLocation
    else -> WeatherAdvantage.Neither
  }

  private fun lowerWins(first: Int, second: Int): WeatherAdvantage =
    lowerWins(first = first.toDouble(), second = second.toDouble())
}
