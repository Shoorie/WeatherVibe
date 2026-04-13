package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingMusicHint
import com.weather.vibe.feature.home.ui.HomeTextStyles

@Composable
internal fun BriefingActionRow(
  modifier: Modifier = Modifier,
  showHint: Boolean,
  onMusicClick: () -> Unit
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (showHint) {
      Text(
        text = aiBriefingMusicHint(),
        style = typography.bodySmall,
        color = HomeTextStyles.mutedOnPrimaryContainer()
      )
      Spacer(modifier = Modifier.width(ExtraSmall))
    }
    BriefingMusicButton(onClick = onMusicClick)
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Row(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium)
    ) {
      BriefingActionRow(showHint = true, onMusicClick = {})
    }
  }
}

@PreviewLightDark
@Composable
private fun PreviewWithoutHint() {
  WeatherVibeTheme {
    Row(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium)
    ) {
      BriefingActionRow(showHint = false, onMusicClick = {})
    }
  }
}
