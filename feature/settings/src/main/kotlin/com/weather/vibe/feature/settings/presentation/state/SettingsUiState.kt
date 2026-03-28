package com.weather.vibe.feature.settings.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface SettingsUiState {

  @Immutable
  data object Loading : SettingsUiState

  @Immutable
  data class Loaded(
    val excludedGenres: String,
    val isCelsius: Boolean,
    val personaOptions: List<PersonaOptionUiState>
  ) : SettingsUiState

  @Immutable
  data class Error(val message: String) : SettingsUiState
}
