package com.weather.vibe.feature.activityplanner.ui.component.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.activityplanner.presentation.state.WindowMetricUiState
import com.weather.vibe.feature.activityplanner.preview.WindowMetricTilePreview

@Composable
internal fun WindowMetricTile(
  modifier: Modifier = Modifier,
  metric: WindowMetricUiState
) {
  Column(
    modifier = modifier
      .semantics(mergeDescendants = true) { contentDescription = metric.contentDescription },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = metric.label,
      color = colors.onSurfaceVariant,
      style = typography.labelSmall
    )
    Text(
      text = "${metric.icon}  ${metric.value}",
      color = colors.onBackground,
      style = typography.titleMedium
    )
    Text(
      text = metric.caption,
      color = colors.onSurfaceVariant,
      style = typography.labelSmall
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WindowMetricTilePreview::class)
  metric: WindowMetricUiState
) {
  WeatherVibeTheme {
    WindowMetricTile(metric = metric)
  }
}
