package com.weather.vibe.feature.home.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.home.presentation.HomeUiState
import com.weather.vibe.feature.home.presentation.SearchState

@Immutable
internal data class HomePreviewParams(
  val searchState: SearchState = SearchState(),
  val state: HomeUiState
)
