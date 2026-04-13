package com.weather.vibe.feature.home.ui.component.daily

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRangeBarHeight
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRangeBarThickness
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyRangeDotRingPx

@Composable
internal fun DailyRangeBar(
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
private fun Preview() {
  WeatherVibeTheme {
    DailyRangeBar(
      modifier = Modifier.padding(Medium),
      range = DailyRangeUiState(
        startFraction = 0.3f,
        endFraction = 0.85f,
        currentFraction = 0.55f
      )
    )
  }
}
