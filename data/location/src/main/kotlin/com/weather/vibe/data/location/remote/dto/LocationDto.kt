package com.weather.vibe.data.location.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(

  @SerialName("admin1")
  val admin1: String? = null,

  @SerialName("country")
  val country: String? = null,

  @SerialName("country_code")
  val countryCode: String? = null,

  @SerialName("id")
  val id: Long,

  @SerialName("latitude")
  val latitude: Double,

  @SerialName("longitude")
  val longitude: Double,

  @SerialName("name")
  val name: String,
)
