package com.weather.vibe.domain.activityplanner.usecase

import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

@Factory
class ApproximateHourlyUvIndex {

  operator fun invoke(
    hour: LocalDateTime,
    sunrise: LocalDateTime?,
    sunset: LocalDateTime?,
    dailyMaxUvIndex: Double
  ): Double {
    if (sunrise == null || sunset == null) return NIGHT_UV
    if (hour.isBefore(sunrise) || hour.isAfter(sunset)) return NIGHT_UV
    return dailyMaxUvIndex * intensityFactor(hour, sunrise, sunset)
  }

  private fun intensityFactor(
    hour: LocalDateTime,
    sunrise: LocalDateTime,
    sunset: LocalDateTime
  ): Double {

    val solarNoon = midpoint(sunrise, sunset)
    val halfDayMinutes = minutesBetween(sunrise, solarNoon)
    if (halfDayMinutes == 0L) return 0.0

    val distanceFromNoon = abs(minutesBetween(solarNoon, hour)).toDouble()
    return (1.0 - distanceFromNoon / halfDayMinutes).coerceIn(0.0, 1.0)
  }

  private fun midpoint(start: LocalDateTime, end: LocalDateTime): LocalDateTime =
    start.plusMinutes(minutesBetween(start, end) / 2)

  private fun minutesBetween(start: LocalDateTime, end: LocalDateTime): Long =
    Duration.between(start, end).toMinutes()

  private companion object {
    const val NIGHT_UV = 0.0
  }
}
