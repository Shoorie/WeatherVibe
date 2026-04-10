package com.weather.vibe.domain.location.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
  val id: Long,
  val name: String,
  val admin1: String?,
  val country: String,
  val latitude: Double,
  val longitude: Double
)
