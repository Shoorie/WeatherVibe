package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface BriefingUiState {

  @Immutable
  data class Error(val canRetry: Boolean) : BriefingUiState

  @Immutable
  data class Loaded(
    val persona: BriefingPersonaUiState,
    val text: String,
    val outfit: String? = null
  ) : BriefingUiState

  @Immutable
  data class Limit(
    val persona: BriefingPersonaUiState,
    val teaser: String,
    val outfit: String? = null
  ) : BriefingUiState

  @Immutable
  data object Loading : BriefingUiState
}

internal val BriefingUiState.persona: BriefingPersonaUiState?
  get() = when (this) {
    is BriefingUiState.Loaded -> persona
    is BriefingUiState.Limit -> persona
    is BriefingUiState.Error, BriefingUiState.Loading -> null
  }
