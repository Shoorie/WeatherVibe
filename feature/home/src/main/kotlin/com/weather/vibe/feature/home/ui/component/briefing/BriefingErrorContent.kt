package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode.Companion.Polite
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingRetryContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingRetryLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingUnavailable
import com.weather.vibe.feature.home.ui.HomeTextStyles.mutedOnPrimaryContainer

@Composable
internal fun BriefingErrorContent(
  modifier: Modifier = Modifier,
  canRetry: Boolean,
  onRetryClick: () -> Unit
) {
  val retryContentDescription = aiBriefingRetryContentDescription()
  Column(
    modifier = modifier
      .fillMaxWidth()
      .semantics { liveRegion = Polite },
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = aiBriefingUnavailable(),
      style = typography.bodyMedium,
      color = mutedOnPrimaryContainer()
    )
    if (canRetry) {
      TextButton(
        modifier = Modifier
          .semantics { contentDescription = retryContentDescription },
        onClick = onRetryClick
      ) {
        Text(
          text = aiBriefingRetryLabel(),
          style = typography.labelMedium,
          color = colors.accent
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingErrorContent(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium),
      canRetry = true,
      onRetryClick = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewWithoutRetry() {
  WeatherVibeTheme {
    BriefingErrorContent(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium),
      canRetry = false,
      onRetryClick = {}
    )
  }
}
