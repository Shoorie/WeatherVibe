package com.weather.vibe.domain.viberating.model

import com.weather.vibe.domain.weather.model.Condition

data class ConditionAverage(
  val condition: Condition,
  val averageRating: Double,
  val entryCount: Int
)
