package com.weather.vibe.data.location.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResultDto(
  val admin1: String? = null,
  val country: String? = null,

  @SerialName("country_code")
  val countryCode: String? = null,

  val id: Long,
  val latitude: Double,
  val longitude: Double,
  val name: String
)
