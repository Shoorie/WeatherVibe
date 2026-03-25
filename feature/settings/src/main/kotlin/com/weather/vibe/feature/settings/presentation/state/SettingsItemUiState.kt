package com.weather.vibe.feature.settings.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class SettingsItemUiState(
  val id: String,
  val title: String
)

