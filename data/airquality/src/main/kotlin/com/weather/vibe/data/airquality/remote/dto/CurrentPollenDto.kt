package com.weather.vibe.data.airquality.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentPollenDto(

  @SerialName("time")
  val time: String,

  @SerialName("alder_pollen")
  val alderPollen: Double? = null,

  @SerialName("birch_pollen")
  val birchPollen: Double? = null,

  @SerialName("grass_pollen")
  val grassPollen: Double? = null,

  @SerialName("mugwort_pollen")
  val mugwortPollen: Double? = null,

  @SerialName("olive_pollen")
  val olivePollen: Double? = null,

  @SerialName("ragweed_pollen")
  val ragweedPollen: Double? = null
)
