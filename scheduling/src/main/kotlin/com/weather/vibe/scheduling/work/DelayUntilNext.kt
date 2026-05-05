package com.weather.vibe.scheduling.work

import com.weather.vibe.core.time.TimeProvider
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

internal fun delayUntilNext(
  target: LocalTime,
  timeProvider: TimeProvider
): Duration {

  val now = timeProvider.now()
  val today = LocalDateTime.of(now.toLocalDate(), target)
  val next = if (now.isBefore(today)) today else today.plusDays(1)

  return Duration.between(now, next)
}
