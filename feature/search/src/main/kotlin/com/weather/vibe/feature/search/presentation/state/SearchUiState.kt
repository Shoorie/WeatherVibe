package com.weather.vibe.feature.search.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface SearchUiState {

  @Immutable
  data object Idle : SearchUiState

  @Immutable
  data class Recents(
    val locations: List<LocationItemUiState>
  ) : SearchUiState

  @Immutable
  data class Results(
    val locations: List<LocationItemUiState>
  ) : SearchUiState

  @Immutable
  data object Searching : SearchUiState

  @Immutable
  data class Empty(val query: String) : SearchUiState
}
