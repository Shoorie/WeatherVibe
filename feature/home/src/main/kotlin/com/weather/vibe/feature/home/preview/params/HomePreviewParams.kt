package com.weather.vibe.feature.home.preview.params

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.home.presentation.state.HomeUiState

@Immutable
internal data class HomePreviewParams(
  val state: HomeUiState
)
