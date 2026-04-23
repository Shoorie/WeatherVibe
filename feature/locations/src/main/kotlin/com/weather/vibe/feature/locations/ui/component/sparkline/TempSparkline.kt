package com.weather.vibe.feature.locations.ui.component.sparkline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.locations.ui.LocationsDefaults

@Composable
internal fun TempSparkline(
  modifier: Modifier = Modifier,
  points: List<Float>,
  color: Color = colors.accent
) {
  val minValue = remember(points) { points.min() }
  val maxValue = remember(points) { points.max() }
  val range = (maxValue - minValue).takeIf { it > 0f } ?: 1f
  Canvas(
    modifier = modifier.size(
      width = LocationsDefaults.SparklineWidth,
      height = LocationsDefaults.SparklineHeight
    )
  ) {
    val offsets = points.mapIndexed { index, value ->
      val x = index / (points.size - 1f) * size.width
      val normalized = (value - minValue) / range
      val y = size.height - normalized * size.height
      Offset(x = x, y = y)
    }
    drawPath(
      path = buildPath(offsets),
      color = color,
      style = Stroke(
        width = LocationsDefaults.SparklineStrokeWidth.toPx(),
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )
  }
}

private fun buildPath(offsets: List<Offset>): Path = Path().apply {
  offsets.forEachIndexed { index, offset ->
    if (index == 0) moveTo(offset.x, offset.y) else lineTo(offset.x, offset.y)
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    TempSparkline(points = listOf(3f, 5f, 8f, 12f, 14f, 13f, 10f, 7f, 4f))
  }
}
