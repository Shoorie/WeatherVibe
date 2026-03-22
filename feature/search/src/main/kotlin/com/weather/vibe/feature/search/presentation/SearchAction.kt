package com.weather.vibe.feature.search.presentation

import com.weather.vibe.feature.search.presentation.state.LocationItemUiState

internal sealed interface SearchAction {
  data object BackClick : SearchAction
  data class LocationSelect(val location: LocationItemUiState) : SearchAction
  data class QueryChange(val query: String) : SearchAction
}
