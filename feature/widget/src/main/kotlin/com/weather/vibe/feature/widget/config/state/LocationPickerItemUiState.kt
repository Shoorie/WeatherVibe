package com.weather.vibe.feature.widget.config.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class LocationPickerItemUiState(
  val id: Long,
  val name: String,
  val subtitle: String
)
