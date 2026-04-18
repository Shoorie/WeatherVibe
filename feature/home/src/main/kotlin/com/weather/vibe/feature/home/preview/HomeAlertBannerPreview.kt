package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.HomeAlertUiState
import com.weather.vibe.feature.home.ui.HomeAirQualityResources.Emojis

internal class HomeAlertBannerPreview : PreviewParameterProvider<HomeAlertUiState> {

  override val values: Sequence<HomeAlertUiState> = sequenceOf(
    HomeAlertUiState(
      indicator = Emojis.warning(),
      title = "Smog warning",
      message = "Very poor smog — consider limiting outdoor time.",
      contentDescription = "Air quality alert: Very poor smog, index 110"
    ),
    HomeAlertUiState(
      indicator = Emojis.warning(),
      title = "Pollen warning",
      message = "High levels of birch, grass — allergy sufferers, take care.",
      contentDescription = "Pollen alert: high levels of birch, grass"
    )
  )
}
