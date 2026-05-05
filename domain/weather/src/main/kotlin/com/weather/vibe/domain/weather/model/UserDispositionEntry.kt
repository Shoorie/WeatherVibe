package com.weather.vibe.domain.weather.model

data class UserDispositionEntry(
  val note: String?,
  val rating: Int,
  val recordedAtEpochMillis: Long
)
