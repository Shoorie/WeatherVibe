package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface BriefingUiState {

  @Immutable
  data object Error : BriefingUiState

  @Immutable
  data class Loaded(val text: String) : BriefingUiState

  @Immutable
  data object Loading : BriefingUiState
}
