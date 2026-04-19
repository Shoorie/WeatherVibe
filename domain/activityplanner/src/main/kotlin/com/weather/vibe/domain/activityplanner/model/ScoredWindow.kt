package com.weather.vibe.domain.activityplanner.model

import java.time.LocalDateTime

data class ScoredWindow(
  val start: LocalDateTime,
  val end: LocalDateTime,
  val averageScore: Int,
  val averageTemperature: Double,
  val averageUvIndex: Double,
  val averageWindSpeed: Double,
  val maxPrecipitationProbability: Int
)
