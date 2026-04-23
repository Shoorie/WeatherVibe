package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationWeatherAdvantage
import com.weather.vibe.domain.location.model.LocationWeatherAdvantage.FirstLocation
import com.weather.vibe.domain.location.model.LocationWeatherAdvantage.Neither
import com.weather.vibe.domain.location.model.LocationWeatherAdvantage.SecondLocation
import com.weather.vibe.domain.location.model.LocationWeatherComparison
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import org.koin.core.annotation.Factory

@Factory
class CompareLocationWeather {

  operator fun invoke(
    first: LocationWeatherSnapshot,
    second: LocationWeatherSnapshot
  ): LocationWeatherComparison =
    LocationWeatherComparison(
      temperature = higherWins(
        first = first.temperatureC,
        second = second.temperatureC
      ),
      wind = lowerWins(
        first = first.windKph,
        second = second.windKph
      ),
      humidity = lowerWins(
        first = first.humidityPercent,
        second = second.humidityPercent
      ),
      rain = lowerWins(
        first = first.precipitationChancePercent,
        second = second.precipitationChancePercent
      )
    )

  private fun higherWins(first: Double, second: Double): LocationWeatherAdvantage =
    when {
      first > second -> FirstLocation
      second > first -> SecondLocation
      else -> Neither
    }

  private fun lowerWins(first: Double, second: Double): LocationWeatherAdvantage = when {
    first < second -> FirstLocation
    second < first -> SecondLocation
    else -> Neither
  }

  private fun lowerWins(first: Int, second: Int): LocationWeatherAdvantage =
    lowerWins(
      first = first.toDouble(),
      second = second.toDouble()
    )
}
