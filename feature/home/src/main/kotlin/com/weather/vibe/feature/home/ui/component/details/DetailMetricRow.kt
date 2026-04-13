package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMetric
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity

@Composable
internal fun DetailMetricRow(
  modifier: Modifier = Modifier,
  item: MetricItemUiState
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = Small)
      .semantics(mergeDescendants = true) {},
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = item.icon,
      fontSize = EmojiMetric
    )
    Spacer(modifier = Modifier.width(Small))
    Text(
      text = item.label,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = item.value,
      style = typography.titleMedium,
      color = colors.onBackground
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    DetailMetricRow(
      modifier = Modifier.padding(Medium),
      item = MetricItemUiState(
        icon = humidity(),
        label = "Humidity",
        value = "65%"
      )
    )
  }
}
