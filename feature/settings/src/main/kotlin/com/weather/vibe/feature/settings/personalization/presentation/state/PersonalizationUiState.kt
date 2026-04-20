package com.weather.vibe.feature.settings.personalization.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

internal sealed interface PersonalizationUiState {

  @Immutable
  data class Loaded(
    val briefToneOptions: ImmutableList<BriefToneOptionUiState>,
    val genreChips: ImmutableList<GenreChipUiState>,
    val hasExcludedGenres: Boolean,
    val isCelsius: Boolean
  ) : PersonalizationUiState

  @Immutable
  data class Error(val message: String) : PersonalizationUiState
}
