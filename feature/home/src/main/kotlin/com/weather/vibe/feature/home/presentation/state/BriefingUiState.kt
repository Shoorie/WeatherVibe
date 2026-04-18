package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface BriefingUiState {

  @Immutable
  data class Error(val canRetry: Boolean) : BriefingUiState

  @Immutable
  data class Loaded(val text: String, val outfit: String? = null) : BriefingUiState

  @Immutable
  data object Loading : BriefingUiState
}
