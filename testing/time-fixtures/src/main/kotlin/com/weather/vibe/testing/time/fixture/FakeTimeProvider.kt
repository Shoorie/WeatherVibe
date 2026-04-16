package com.weather.vibe.testing.time.fixture

import com.weather.vibe.core.time.TimeProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class FakeTimeProvider(
  var current: LocalDateTime = DEFAULT_NOW
) : TimeProvider {

  override fun now(): LocalDateTime =
    current

  override fun today(): LocalDate =
    current.toLocalDate()

  override fun nowEpochMillis(): Long =
    current.toInstant(ZoneOffset.UTC).toEpochMilli()

  companion object {
    val DEFAULT_NOW: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
  }
}
