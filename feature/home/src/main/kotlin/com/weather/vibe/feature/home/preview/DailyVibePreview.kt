package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.pleasantVibe
import com.weather.vibe.feature.home.preview.HomePreviewData.roughVibe

internal class DailyVibePreview : PreviewParameterProvider<DailyVibeUiState> {
  override val values: Sequence<DailyVibeUiState> =
    sequenceOf(pleasantVibe, roughVibe)
}
