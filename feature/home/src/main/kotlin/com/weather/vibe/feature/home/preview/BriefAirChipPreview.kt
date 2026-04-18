package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.ui.HomeAirQualityResources.Emojis

internal class BriefAirChipPreview : PreviewParameterProvider<AirQualityChipUiState> {

  override val values: Sequence<AirQualityChipUiState> = sequenceOf(
    AirQualityChipUiState(
      indicator = Emojis.aqiGood(),
      label = "Good air",
      contentDescription = "Air quality: Good air, index 15"
    ),
    AirQualityChipUiState(
      indicator = Emojis.aqiModerate(),
      label = "Moderate smog",
      contentDescription = "Air quality: Moderate smog, index 55"
    ),
    AirQualityChipUiState(
      indicator = Emojis.aqiVeryPoor(),
      label = "Very poor smog",
      contentDescription = "Air quality: Very poor smog, index 95"
    )
  )
}
