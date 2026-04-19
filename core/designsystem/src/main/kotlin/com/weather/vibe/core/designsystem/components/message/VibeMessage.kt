package com.weather.vibe.core.designsystem.components.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode.Companion.Polite
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibeMessage(
  modifier: Modifier = Modifier,
  message: String,
  title: String? = null,
  announceLive: Boolean = false,
  action: (@Composable () -> Unit)? = null
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(Medium)
      .semantics { if (announceLive) liveRegion = Polite },
    verticalArrangement = Arrangement.spacedBy(Small),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (title != null) {
      Text(
        text = title,
        color = colors.onBackground,
        style = typography.titleMedium,
        textAlign = TextAlign.Center
      )
    }
    Text(
      text = message,
      color = colors.onSurfaceVariant,
      style = typography.bodyMedium,
      textAlign = TextAlign.Center
    )
    action?.invoke()
  }
}

@PreviewLightDark
@Composable
private fun PreviewError() {
  WeatherVibeTheme {
    VibeMessage(
      title = "Something went wrong",
      message = "Couldn't load activity plan.",
      announceLive = true
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewEmpty() {
  WeatherVibeTheme {
    VibeMessage(message = "No great windows for running today.")
  }
}
