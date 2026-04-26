package com.weather.vibe.feature.home.ui.component.vibe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.DailyVibeCardUiState
import com.weather.vibe.feature.home.preview.DailyVibePreview
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.dailyVibeSectionLabel
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyVibeMinHeight
import com.weather.vibe.feature.home.ui.component.airquality.AirQualityChipRow

@Composable
internal fun DailyVibeCard(
  modifier: Modifier = Modifier,
  canShare: Boolean,
  onShareClick: () -> Unit,
  state: DailyVibeCardUiState
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = dailyVibeSectionLabel(),
    uppercase = true
  ) {
    VibeCard(modifier = Modifier.heightIn(min = DailyVibeMinHeight)) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
              heading()
              contentDescription = state.vibe.contentDescription
            },
          horizontalArrangement = Arrangement.spacedBy(Medium),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            modifier = Modifier.clearAndSetSemantics {},
            text = state.vibe.emoji,
            style = typography.headlineLarge
          )
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ExtraSmall)
          ) {
            Text(
              text = state.vibe.summary,
              color = colors.onPrimaryContainer,
              style = typography.titleMedium
            )
            Text(
              text = state.vibe.oneLiner,
              color = colors.onPrimaryContainer,
              style = typography.bodySmall
            )
          }
        }
        if (state.airQualityChip != null || state.pollenChip != null) {
          AirQualityChipRow(
            airQualityChip = state.airQualityChip,
            pollenChip = state.pollenChip
          )
        }
        if (canShare) {
          Spacer(modifier = Modifier.height(Medium))
          ShareBriefActionRow(onShareClick = onShareClick)
        }
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyVibePreview::class)
  state: DailyVibeCardUiState
) {
  WeatherVibeTheme {
    DailyVibeCard(
      canShare = true,
      onShareClick = {},
      state = state
    )
  }
}
