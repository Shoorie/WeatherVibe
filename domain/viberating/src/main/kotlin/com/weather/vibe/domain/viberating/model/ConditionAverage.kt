package com.weather.vibe.domain.viberating.model

data class ConditionAverage(
  val condition: Condition,
  val averageRating: Double,
  val entryCount: Int
)
