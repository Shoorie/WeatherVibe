package com.weather.vibe.domain.weather.model

data class LocationResult(
  val id: Long,
  val name: String,
  val latitude: Double,
  val longitude: Double,
  val country: String,
  val admin1: String?
)
