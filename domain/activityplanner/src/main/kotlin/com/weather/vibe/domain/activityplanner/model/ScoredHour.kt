package com.weather.vibe.domain.activityplanner.model

import java.time.LocalDateTime

data class ScoredHour(
  val time: LocalDateTime,
  val score: Int,
  val temperature: Double,
  val uvIndex: Double,
  val windSpeed: Double,
  val precipitationProbability: Int,
  val reasons: List<ScoreReason>
)
