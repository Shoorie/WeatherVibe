package com.weather.vibe.feature.settings.personalization.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

internal sealed interface PersonalizationUiState {

  @Immutable
  data object Loading : PersonalizationUiState

  @Immutable
  data class Loaded(
    val genreChips: ImmutableList<GenreChipUiState>,
    val hasExcludedGenres: Boolean,
    val isCelsius: Boolean,
    val isPremium: Boolean,
    val narrator: NarratorUiState,
    val premiumToneCount: Int,
    val paywall: PaywallUiState?,
    val personas: ImmutableList<PersonaUiState>
  ) : PersonalizationUiState

  @Immutable
  data class Error(val message: String) : PersonalizationUiState
}
