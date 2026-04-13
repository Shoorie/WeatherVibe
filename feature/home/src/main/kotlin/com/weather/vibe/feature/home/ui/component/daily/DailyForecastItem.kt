package com.weather.vibe.feature.home.ui.component.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.preview.DailyForecastPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRowBarWeight
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRowDayWeight
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRowTempWeight
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiSmall
import com.weather.vibe.feature.home.ui.HomeTextStyles.dayColor
import com.weather.vibe.feature.home.ui.HomeTextStyles.dayStyle
import com.weather.vibe.feature.home.ui.HomeTextStyles.semiBold

@Composable
internal fun DailyForecastItem(
  modifier: Modifier = Modifier,
  state: DailyForecastUiState
) {

  val dayColor = dayColor(isToday = state.isToday)
  val dayStyle = dayStyle(isToday = state.isToday)
  val maxStyle = semiBold(typography.bodyMedium)

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = Small)
      .semantics(mergeDescendants = true) { contentDescription = state.contentDescription },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      modifier = Modifier.weight(DailyRowDayWeight),
      text = state.dayLabel,
      style = dayStyle,
      color = dayColor
    )
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = state.conditionEmoji,
      fontSize = EmojiSmall,
      textAlign = TextAlign.Center
    )
    Text(
      modifier = Modifier.weight(DailyRowTempWeight),
      text = state.minTemperature,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.End
    )
    DailyRangeBar(
      modifier = Modifier
        .weight(DailyRowBarWeight)
        .padding(horizontal = Small),
      range = state.range
    )
    Text(
      modifier = Modifier.weight(DailyRowTempWeight),
      text = state.maxTemperature,
      style = maxStyle,
      color = colors.onBackground,
      textAlign = TextAlign.End
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyForecastPreview::class)
  state: DailyForecastUiState
) {
  WeatherVibeTheme {
    DailyForecastItem(
      modifier = Modifier.padding(Medium),
      state = state
    )
  }
}
