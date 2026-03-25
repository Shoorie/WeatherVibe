package com.weather.vibe.feature.settings.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface SettingsUiState {

  @Immutable
  data object Loading : SettingsUiState

  @Immutable
  data class Loaded(val items: List<SettingsItemUiState>) : SettingsUiState

  @Immutable
  data class Error(val message: String) : SettingsUiState
}

