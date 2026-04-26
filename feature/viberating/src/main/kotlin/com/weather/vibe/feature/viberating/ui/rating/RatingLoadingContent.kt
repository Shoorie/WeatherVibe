package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.cardTitle

@Composable
internal fun RatingLoadingContent() {
  Text(
    text = cardTitle(),
    style = typography.titleMedium,
    color = colors.onSurface
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    RatingLoadingContent()
  }
}
