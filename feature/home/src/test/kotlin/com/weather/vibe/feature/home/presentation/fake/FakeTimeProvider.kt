package com.weather.vibe.feature.home.presentation.fake

import com.weather.vibe.core.time.TimeProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

internal class FakeTimeProvider(
  var current: LocalDateTime = LocalDateTime.of(
    /* year = */ 2026,
    /* month = */ 4,
    /* dayOfMonth = */ 8,
    /* hour = */ 12,
    /* minute = */ 0
  )
) : TimeProvider {

  override fun now(): LocalDateTime =
    current

  override fun today(): LocalDate =
    current.toLocalDate()

  override fun nowEpochMillis(): Long =
    current.toInstant(ZoneOffset.UTC).toEpochMilli()
}
