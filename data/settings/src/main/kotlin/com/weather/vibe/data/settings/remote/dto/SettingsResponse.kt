package com.weather.vibe.data.settings.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingsResponse(
  @SerialName("id") val id: String,
  @SerialName("title") val title: String
)

