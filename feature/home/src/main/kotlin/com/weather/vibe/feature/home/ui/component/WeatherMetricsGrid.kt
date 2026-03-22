package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.preview.MetricsPreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun WeatherMetricsGrid(
  modifier: Modifier = Modifier,
  state: MetricsUiState
) {
  FlowRow(
    modifier = modifier.fillMaxWidth(),
    maxItemsInEachRow = GRID_COLUMNS,
    horizontalArrangement = Arrangement.spacedBy(PaddingSmall),
    verticalArrangement = Arrangement.spacedBy(PaddingSmall)
  ) {
    state.items.forEach { item ->
      WeatherMetricCard(
        modifier = Modifier.weight(1f),
        icon = item.icon,
        value = item.value,
        label = item.label
      )
    }
  }
}

private const val GRID_COLUMNS = 2

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(MetricsPreview::class)
  state: MetricsUiState
) {
  WeatherVibeTheme {
    WeatherMetricsGrid(state = state)
  }
}
