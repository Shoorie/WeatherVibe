package com.weather.vibe.domain.location.model

data class Location(
  val id: Long,
  val name: String,
  val admin1: String?,
  val country: String,
  val latitude: Double,
  val longitude: Double
)
