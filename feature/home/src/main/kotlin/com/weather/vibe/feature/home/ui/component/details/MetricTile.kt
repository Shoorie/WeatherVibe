package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMetric
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeTextStyles.semiBold

@Composable
internal fun MetricTile(
  modifier: Modifier = Modifier,
  item: MetricItemUiState
) {

  val valueStyle = semiBold(typography.titleMedium)

  Column(
    modifier = modifier
      .clip(shapes.cardSmall)
      .background(colors.surfaceVariant)
      .padding(Medium)
      .semantics(mergeDescendants = true) {}
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = item.label,
        style = typography.labelMedium,
        color = colors.onSurfaceVariant
      )
      Text(
        modifier = Modifier.clearAndSetSemantics {},
        text = item.icon,
        fontSize = EmojiMetric
      )
    }
    Spacer(modifier = Modifier.height(ExtraSmall))
    Text(
      text = item.value,
      style = valueStyle,
      color = colors.onBackground
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    MetricTile(
      modifier = Modifier.padding(Medium),
      item = MetricItemUiState(
        icon = humidity(),
        label = "Humidity",
        value = "65%"
      )
    )
  }
}
