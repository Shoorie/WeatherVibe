package com.weather.vibe.domain.location.model

data class Favorite(
  val id: Long,
  val isDefault: Boolean,
  val label: String?,
  val location: Location,
  val position: Int
)
