package com.weather.vibe.feature.activityplanner.ui.component.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.pill.VibePill
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.feature.activityplanner.presentation.state.WindowCardUiState
import com.weather.vibe.feature.activityplanner.presentation.state.WindowMetricUiState
import com.weather.vibe.feature.activityplanner.preview.WindowCardPreview

@Composable
internal fun WindowCard(
  modifier: Modifier = Modifier,
  window: WindowCardUiState
) {
  VibeCard(
    modifier = modifier
      .semantics(mergeDescendants = true) { contentDescription = window.contentDescription },
    containerColor = colors.rowSurface
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(Medium)) {
      TitleRow(
        timeRange = window.timeRange,
        tierLabel = window.tierLabel,
        tier = window.tier
      )
      MetricsRow(
        temperature = window.temperature,
        uv = window.uv,
        wind = window.wind
      )
    }
  }
}

@Composable
private fun TitleRow(
  timeRange: String,
  tierLabel: String,
  tier: ScoreTier
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = timeRange,
      color = colors.onBackground,
      style = typography.titleMedium
    )
    VibePill(
      text = tierLabel,
      containerColor = scoreTierBackground(tier),
      contentColor = scoreTierForeground(tier)
    )
  }
}

@Composable
private fun MetricsRow(
  temperature: WindowMetricUiState,
  uv: WindowMetricUiState,
  wind: WindowMetricUiState
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    WindowMetricTile(
      modifier = Modifier.weight(1f),
      metric = temperature
    )
    WindowMetricTile(
      modifier = Modifier.weight(1f),
      metric = uv
    )
    WindowMetricTile(
      modifier = Modifier.weight(1f),
      metric = wind
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WindowCardPreview::class)
  window: WindowCardUiState
) {
  WeatherVibeTheme {
    WindowCard(window = window)
  }
}
