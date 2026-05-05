package com.weather.vibe.scheduling.work

import com.weather.vibe.core.time.TimeProvider
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

internal val POLLEN_DAYTIME_SLOTS: List<LocalTime> = listOf(
  LocalTime.of(7, 0),
  LocalTime.of(13, 0),
  LocalTime.of(19, 0)
)

internal fun nextPollenDelay(timeProvider: TimeProvider): Duration {

  val now = timeProvider.now()
  val today = now.toLocalDate()

  val nextToday = POLLEN_DAYTIME_SLOTS
    .map { slot -> LocalDateTime.of(today, slot) }
    .firstOrNull { dateTime -> dateTime.isAfter(now) }

  val next = nextToday
    ?: LocalDateTime.of(today.plusDays(1), POLLEN_DAYTIME_SLOTS.first())

  return Duration.between(now, next)
}
