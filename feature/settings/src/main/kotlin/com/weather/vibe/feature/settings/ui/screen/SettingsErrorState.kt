package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.ui.SettingsDefaults.ErrorIconSize
import com.weather.vibe.feature.settings.ui.SettingsResources.Emojis
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.errorTitle

@Composable
internal fun SettingsErrorState(
  modifier: Modifier = Modifier,
  message: String
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .padding(Padding.Medium),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .clip(shapes.card)
        .background(colors.glassSurface)
        .border(Stroke.Border, colors.outlineVariant, shapes.card)
        .padding(Padding.Large),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(Padding.Small)
    ) {
      Text(
        modifier = Modifier.clearAndSetSemantics {},
        text = Emojis.error(),
        fontSize = ErrorIconSize
      )
      Text(
        text = errorTitle(),
        color = colors.onBackground,
        style = typography.titleSmall,
        textAlign = TextAlign.Center
      )
      Text(
        text = message,
        color = colors.onSurfaceVariant,
        style = typography.bodyMedium,
        textAlign = TextAlign.Center
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsErrorState(message = "Failed to load settings")
  }
}
