package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.BrandButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.BrandColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.openInSpotify
import com.weather.vibe.feature.home.ui.HomePainters.spotifyIcon

@Composable
internal fun SpotifyButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  BrandButton(
    modifier = modifier,
    icon = spotifyIcon(),
    text = openInSpotify(),
    containerColor = BrandColors.SpotifyGreen,
    onClick = onClick
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SpotifyButton(
      modifier = Modifier.padding(Medium),
      onClick = {}
    )
  }
}
