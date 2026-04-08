package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.preview.DailyForecastPreview

@Composable
internal fun DailyForecastItem(
  modifier: Modifier = Modifier,
  state: DailyForecastUiState
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = PaddingSmall)
      .semantics(mergeDescendants = true) {},
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      modifier = Modifier.weight(1f),
      text = state.dayLabel,
      style = typography.bodyMedium,
      color = colors.onBackground
    )
    Text(
      modifier = Modifier
        .weight(1f)
        .clearAndSetSemantics {},
      text = state.conditionEmoji,
      fontSize = EmojiSizeSmall,
      textAlign = TextAlign.Center
    )
    Row(
      modifier = Modifier.weight(1f),
      horizontalArrangement = Arrangement.End,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = state.maxTemperature,
        style = typography.bodyMedium,
        color = colors.onBackground
      )
      Text(
        text = " / ${state.minTemperature}",
        style = typography.bodyMedium,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyForecastPreview::class)
  state: DailyForecastUiState
) {
  WeatherVibeTheme {
    DailyForecastItem(state = state)
  }
}
