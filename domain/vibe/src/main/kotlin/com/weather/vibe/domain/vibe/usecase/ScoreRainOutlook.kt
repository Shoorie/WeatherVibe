package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.weather.model.HourlyWeather
import org.koin.core.annotation.Factory

@Factory
internal class ScoreRainOutlook {

  operator fun invoke(hourly: List<HourlyWeather>): Int {

    val horizon = hourly.take(LOOKAHEAD_HOURS)
    if (horizon.isEmpty()) return NO_PENALTY

    val averageProbability = horizon
      .sumOf { it.precipitationProbability } / horizon.size

    return (averageProbability * WEIGHT / PERCENT_SCALE)
      .coerceAtMost(MAX_PENALTY)
  }

  private companion object {
    const val LOOKAHEAD_HOURS = 6
    const val WEIGHT = 60
    const val PERCENT_SCALE = 100
    const val MAX_PENALTY = 40
    const val NO_PENALTY = 0
  }
}
