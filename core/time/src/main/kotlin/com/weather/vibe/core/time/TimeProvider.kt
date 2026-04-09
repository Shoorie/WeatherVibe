package com.weather.vibe.core.time

import java.time.LocalDate
import java.time.LocalDateTime

interface TimeProvider {
  fun now(): LocalDateTime
  fun today(): LocalDate
  fun nowEpochMillis(): Long
}
