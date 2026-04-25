package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.AMBER
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.GREEN
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.ROSE
import com.weather.vibe.feature.home.ui.HomeAirQualityResources.Emojis

internal class AirQualityChipPreview : PreviewParameterProvider<AirQualityChipUiState> {

  private val good: AirQualityChipUiState =
    AirQualityChipUiState(
      indicator = Emojis.aqiGood(),
      label = "Good air",
      contentDescription = "Air quality: Good air, index 15",
      tint = GREEN
    )

  private val moderate: AirQualityChipUiState =
    AirQualityChipUiState(
      indicator = Emojis.aqiModerate(),
      label = "Moderate smog",
      contentDescription = "Air quality: Moderate smog, index 55",
      tint = AMBER
    )

  private val veryPoor: AirQualityChipUiState =
    AirQualityChipUiState(
      indicator = Emojis.aqiVeryPoor(),
      label = "Very poor smog",
      contentDescription = "Air quality: Very poor smog, index 95",
      tint = ROSE
    )

  override val values: Sequence<AirQualityChipUiState> =
    sequenceOf(good, moderate, veryPoor)
}
