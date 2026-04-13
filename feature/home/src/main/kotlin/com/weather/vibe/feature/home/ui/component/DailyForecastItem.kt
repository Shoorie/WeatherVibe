package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.preview.DailyForecastPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRangeBarHeight
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRangeBarThickness
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRangeDotRingPx
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRowBarWeight
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRowDayWeight
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRowTempWeight
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiSmall

@Composable
internal fun DailyForecastItem(
  modifier: Modifier = Modifier,
  state: DailyForecastUiState
) {
  val dayColor = if (state.isToday) colors.accent else colors.onBackground
  val baseStyle = typography.bodyMedium
  val boldStyle = remember(baseStyle) { baseStyle.copy(fontWeight = FontWeight.SemiBold) }
  val dayStyle = if (state.isToday) boldStyle else baseStyle
  val contentLabel = remember(state) {
    "${state.dayLabel}, ${state.conditionLabel}, ${state.minTemperature} – ${state.maxTemperature}"
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = Padding.Small)
      .semantics(mergeDescendants = true) { contentDescription = contentLabel },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    Text(
      modifier = Modifier.weight(DailyRowDayWeight),
      text = state.dayLabel,
      style = dayStyle,
      color = dayColor,
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
        .padding(horizontal = Padding.Small),
      range = state.range
    )
    Text(
      modifier = Modifier.weight(DailyRowTempWeight),
      text = state.maxTemperature,
      style = boldStyle,
      color = colors.onBackground,
      textAlign = TextAlign.End
    )
  }
}

@Composable
private fun DailyRangeBar(
  modifier: Modifier = Modifier,
  range: DailyRangeUiState
) {
  val trackColor = colors.outlineVariant
  val coolColor = colors.colorCool
  val warmColor = colors.colorWarm
  val dotRingColor = colors.glassSurface
  val dotFillColor = colors.onBackground

  val fillBrush = remember(coolColor, warmColor) {
    Brush.horizontalGradient(colors = listOf(coolColor, warmColor))
  }

  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(DailyRangeBarHeight)
      .clearAndSetSemantics {}
  ) {
    val thicknessPx = DailyRangeBarThickness.toPx()
    val barTop = (size.height - thicknessPx) / 2f
    val barCorner = CornerRadius(thicknessPx / 2f)
    drawRoundRect(
      color = trackColor,
      topLeft = Offset(0f, barTop),
      size = Size(size.width, thicknessPx),
      cornerRadius = barCorner
    )
    val fillStart = size.width * range.startFraction
    val fillWidth = (size.width * (range.endFraction - range.startFraction))
      .coerceAtLeast(thicknessPx)
    drawRoundRect(
      brush = fillBrush,
      topLeft = Offset(fillStart, barTop),
      size = Size(fillWidth, thicknessPx),
      cornerRadius = barCorner
    )
    val currentFraction = range.currentFraction ?: return@Canvas
    val centerX = size.width * currentFraction
    val centerY = size.height / 2f
    val outerRadius = size.height / 2f
    val innerRadius = outerRadius - DailyRangeDotRingPx
    drawCircle(
      color = dotRingColor,
      radius = outerRadius,
      center = Offset(centerX, centerY)
    )
    drawCircle(
      color = dotFillColor,
      radius = innerRadius,
      center = Offset(centerX, centerY)
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
      modifier = Modifier.padding(Padding.Medium),
      state = state
    )
  }
}
