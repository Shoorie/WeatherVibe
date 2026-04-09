package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.core.time.TimeProvider
import org.koin.core.annotation.Factory
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Factory
class FindCurrentHourIndex(
  private val timeProvider: TimeProvider
) {

  operator fun invoke(hours: List<LocalDateTime>): Int {
    val currentHour = currentHour()
    return hours.indexOfFirst { it.startOfHour() == currentHour }
  }

  private fun currentHour(): LocalDateTime =
    timeProvider.now().startOfHour()

  private fun LocalDateTime.startOfHour(): LocalDateTime =
    truncatedTo(ChronoUnit.HOURS)
}
