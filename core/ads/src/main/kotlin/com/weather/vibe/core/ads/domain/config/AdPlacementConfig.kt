package com.weather.vibe.core.ads.domain.config

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AdPlacementConfig(
  @SerialName("enabled") val enabled: Boolean = false
)
