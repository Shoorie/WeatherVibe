package com.weather.vibe.feature.home.ui.component.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.HomeAlertUiState
import com.weather.vibe.feature.home.preview.HomeAlertBannerPreview
import com.weather.vibe.feature.home.ui.HomeTexts.alertSectionLabel

@Composable
internal fun HomeAlertBanner(
  modifier: Modifier = Modifier,
  state: HomeAlertUiState
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = alertSectionLabel(),
    uppercase = true
  ) {
    VibeCard(
      modifier = Modifier
        .fillMaxWidth()
        .clearAndSetSemantics {
          contentDescription = state.contentDescription
          liveRegion = LiveRegionMode.Polite
        },
      shape = shapes.card,
      containerColor = colors.error,
      contentPadding = Medium
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Small),
        verticalAlignment = Alignment.Top
      ) {
        Text(
          text = state.indicator,
          style = typography.titleMedium
        )
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(Small)
        ) {
          Text(
            text = state.title,
            style = typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = colors.onError
          )
          Text(
            text = state.message,
            style = typography.bodyMedium,
            color = colors.onError
          )
        }
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HomeAlertBannerPreview::class)
  state: HomeAlertUiState
) {
  WeatherVibeTheme {
    HomeAlertBanner(state = state)
  }
}
