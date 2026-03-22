package com.weather.vibe.feature.search.preview.params

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.search.presentation.state.SearchUiState

@Immutable
internal data class SearchPreviewParams(
  val query: String,
  val state: SearchUiState
)
