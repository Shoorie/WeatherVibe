package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.afternoonSunInfo
import com.weather.vibe.feature.home.preview.HomePreviewData.nighttimeSunInfo

internal class SunArcSectionPreview :
  PreviewParameterProvider<SunriseSunsetUiState> {

  override val values: Sequence<SunriseSunsetUiState> =
    sequenceOf(afternoonSunInfo, nighttimeSunInfo)
}
