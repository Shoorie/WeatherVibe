package com.weather.vibe.domain.location.model

data class LocationResult(
  val admin1: String?,
  val country: String,
  val id: Long,
  val latitude: Double,
  val longitude: Double,
  val name: String
)
