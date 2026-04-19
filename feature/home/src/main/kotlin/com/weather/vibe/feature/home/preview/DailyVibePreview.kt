package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyVibeCardUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.pleasantDailyVibeCard
import com.weather.vibe.feature.home.preview.HomePreviewData.roughDailyVibeCard

internal class DailyVibePreview : PreviewParameterProvider<DailyVibeCardUiState> {

  override val values: Sequence<DailyVibeCardUiState> =
    sequenceOf(pleasantDailyVibeCard, roughDailyVibeCard)
}
