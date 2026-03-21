package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResultDto(

  @SerialName("country_code")
  val countryCode: String? = null,

  val id: Long,
  val name: String,
  val latitude: Double,
  val longitude: Double,
  val country: String? = null,
  val admin1: String? = null,
)
