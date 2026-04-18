package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface HomeUiState {

  @Immutable
  data object Loading : HomeUiState

  @Immutable
  data class Loaded(
    val aiSuggestion: AiSuggestionSectionUiState = AiSuggestionSectionUiState(),
    val details: DetailsSectionsUiState,
    val environment: EnvironmentSectionUiState = EnvironmentSectionUiState(),
    val forecast: ForecastSectionUiState,
    val isRefreshing: Boolean = false
  ) : HomeUiState

  @Immutable
  data class Error(val message: String) : HomeUiState
}
