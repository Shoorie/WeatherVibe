package com.weather.vibe.feature.settings.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface SettingsUiState {

  @Immutable
  data object Loading : SettingsUiState

  @Immutable
  data class Loaded(
    val alertsEnabled: Boolean,
    val briefToneOptions: List<BriefToneOptionUiState>,
    val genreChips: List<GenreChipSettingsUiState>,
    val hasExcludedGenres: Boolean,
    val isCelsius: Boolean,
    val morningBriefEnabled: Boolean
  ) : SettingsUiState

  @Immutable
  data class Error(val message: String) : SettingsUiState
}
