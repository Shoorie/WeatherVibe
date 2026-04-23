package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.locations.ui.LocationsDefaults.MiniSparklineHeight
import com.weather.vibe.feature.locations.ui.LocationsDefaults.MiniSparklineStroke
import com.weather.vibe.feature.locations.ui.LocationsDefaults.MiniSparklineWidth
import com.weather.vibe.feature.locations.ui.util.buildSmoothPath
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LocationMiniSparkline(
  modifier: Modifier = Modifier,
  points: ImmutableList<Float>,
  color: Color = colors.accent
) {
  if (points.size < 2) return
  val minValue = points.min()
  val maxValue = points.max()
  val range = (maxValue - minValue).takeIf { it > 0f } ?: 1f
  Canvas(
    modifier = modifier.size(
      width = MiniSparklineWidth,
      height = MiniSparklineHeight
    )
  ) {
    val offsets = points.mapIndexed { index, value ->
      val x = index / (points.size - 1f) * size.width
      val normalized = (value - minValue) / range
      val y = size.height - normalized * size.height
      Offset(x = x, y = y)
    }
    drawPath(
      path = buildSmoothPath(offsets = offsets),
      color = color,
      style = Stroke(
        width = MiniSparklineStroke.toPx(),
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationMiniSparkline(
      points = persistentListOf(3f, 5f, 8f, 11f, 12f, 10f, 7f, 4f, 2f)
    )
  }
}
