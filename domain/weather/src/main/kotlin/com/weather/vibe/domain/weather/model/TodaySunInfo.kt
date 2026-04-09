package com.weather.vibe.domain.weather.model

import java.time.Duration
import java.time.LocalDateTime

data class TodaySunInfo(
  val dayLength: Duration,
  val sunProgress: Float,
  val sunrise: LocalDateTime,
  val sunset: LocalDateTime
)
