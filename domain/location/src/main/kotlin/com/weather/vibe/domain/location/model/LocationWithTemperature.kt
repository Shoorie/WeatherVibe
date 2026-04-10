package com.weather.vibe.domain.location.model

data class LocationWithTemperature(
  val location: Location,
  val currentTemperature: Double? = null
)
