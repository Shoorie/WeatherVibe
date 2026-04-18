package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis

internal class SharePosterPreviewProvider : PreviewParameterProvider<SharePosterUiState> {

  private val sunny: SharePosterUiState =
    SharePosterUiState(
      cityName = "Warsaw",
      conditionEmoji = Emojis.sunny(),
      conditionLabel = "Mainly clear",
      dateLabel = "Saturday, 18 April",
      gradientKey = ShareGradientKey.SUNNY,
      outfit = "Light tee and sunglasses",
      quoteText = "Sky wide open — the kind of day to trade the couch for a park bench.",
      temperature = "23°",
      wordmarkHeadline = "WeatherVibe"
    )

  private val rainy: SharePosterUiState =
    sunny.copy(
      conditionEmoji = Emojis.rainfall(),
      conditionLabel = "Rain showers",
      gradientKey = ShareGradientKey.RAINY,
      outfit = "Hooded jacket and boots",
      quoteText = "Soft rain wraps the city — coffee, long read, slower pace.",
      temperature = "12°"
    )

  private val stormy: SharePosterUiState =
    sunny.copy(
      cityName = "Prague",
      conditionEmoji = Emojis.thunderstorm(),
      conditionLabel = "Thunderstorm",
      gradientKey = ShareGradientKey.STORMY,
      outfit = "Stay in — warm sweater",
      quoteText = "Thunder on the horizon. Stay cozy — the sky is putting on a show tonight.",
      temperature = "15°"
    )

  private val snowy: SharePosterUiState =
    sunny.copy(
      cityName = "Helsinki",
      conditionEmoji = Emojis.snow(),
      conditionLabel = "Snow",
      gradientKey = ShareGradientKey.SNOWY,
      outfit = "Parka, boots, gloves",
      quoteText = "Powdered rooftops, slow traffic, fast cocoa. Winter doing its thing.",
      temperature = "-4°"
    )

  private val night: SharePosterUiState =
    sunny.copy(
      cityName = "Kraków",
      conditionEmoji = Emojis.moon(),
      conditionLabel = "Clear night",
      gradientKey = ShareGradientKey.NIGHT,
      outfit = null,
      quoteText = "Still night air, clear sky. A perfect window for a walk under the stars.",
      temperature = "8°"
    )

  override val values: Sequence<SharePosterUiState> =
    sequenceOf(sunny, rainy, stormy, snowy, night)
}
