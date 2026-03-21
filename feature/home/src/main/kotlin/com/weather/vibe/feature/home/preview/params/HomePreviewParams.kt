package com.weather.vibe.feature.home.preview.params

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.SearchState

@Immutable
internal data class HomePreviewParams(
  val searchState: SearchState = SearchState(),
  val state: HomeUiState
)
