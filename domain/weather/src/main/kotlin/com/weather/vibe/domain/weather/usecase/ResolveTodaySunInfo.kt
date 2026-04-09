package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.TodaySunInfo
import org.koin.core.annotation.Factory

@Factory
class ResolveTodaySunInfo(
  private val calculateDayLength: CalculateDayLength,
  private val calculateSunProgress: CalculateSunProgress
) {

  operator fun invoke(days: List<DailyWeather>): TodaySunInfo? {

    val today = days.firstOrNull() ?: return null
    val sunrise = today.sunrise ?: return null
    val sunset = today.sunset ?: return null

    return TodaySunInfo(
      dayLength = calculateDayLength(sunrise = sunrise, sunset = sunset),
      sunProgress = calculateSunProgress(sunrise = sunrise, sunset = sunset),
      sunrise = sunrise,
      sunset = sunset
    )
  }
}
