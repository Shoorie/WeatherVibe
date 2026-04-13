package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
internal fun BriefingTextContent(
  modifier: Modifier = Modifier,
  text: String
) {
  Text(
    text = text,
    style = typography.bodyMedium,
    color = colors.onPrimaryContainer,
    modifier = modifier.fillMaxWidth()
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingTextContent(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium),
      text = "A mild partly cloudy day with a light breeze — great for " +
        "a walk before the evening rain."
    )
  }
}
