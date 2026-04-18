package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingOutfitLabel

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun BriefingOutfitLine(
  modifier: Modifier = Modifier,
  outfit: String
) {
  FlowRow(
    modifier = modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {
        liveRegion = LiveRegionMode.Polite
      },
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = aiBriefingOutfitLabel(),
      style = typography.bodyMedium,
      fontWeight = FontWeight.SemiBold,
      color = colors.onPrimaryContainer
    )
    Text(
      text = outfit,
      style = typography.bodyMedium,
      color = colors.onPrimaryContainer
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingOutfitLine(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium),
      outfit = "Light jacket, umbrella, sneakers"
    )
  }
}
