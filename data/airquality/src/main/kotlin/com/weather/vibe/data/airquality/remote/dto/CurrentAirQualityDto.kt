package com.weather.vibe.data.airquality.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentAirQualityDto(

  @SerialName("time")
  val time: String,

  @SerialName("european_aqi")
  val europeanAqi: Int
)
