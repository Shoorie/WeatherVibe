package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherSuggestionDto(

  @SerialName("briefText")
  val briefText: String = "",

  @SerialName("genres")
  val genres: List<String> = emptyList(),

  @SerialName("mood")
  val mood: String = "",

  @SerialName("moodDescription")
  val moodDescription: String = ""
)
