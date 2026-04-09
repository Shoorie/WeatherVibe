package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.core.time.TimeProvider
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDateTime

@Factory
class CalculateSunProgress(
  private val timeProvider: TimeProvider
) {

  operator fun invoke(sunrise: LocalDateTime, sunset: LocalDateTime): Float {

    val dayLength = minutesBetween(sunrise, sunset)
    if (dayLength <= 0L) return MIN_PROGRESS
    val elapsed = minutesSince(sunrise)

    return fractionOfDay(elapsed = elapsed, dayLength = dayLength)
  }

  private fun minutesBetween(start: LocalDateTime, end: LocalDateTime): Long =
    Duration.between(start, end).toMinutes()

  private fun minutesSince(start: LocalDateTime): Long =
    Duration.between(start, timeProvider.now()).toMinutes()

  private fun fractionOfDay(elapsed: Long, dayLength: Long): Float =
    (elapsed.toFloat() / dayLength.toFloat())
      .coerceIn(MIN_PROGRESS, MAX_PROGRESS)

  private companion object {
    const val MIN_PROGRESS = 0f
    const val MAX_PROGRESS = 1f
  }
}
