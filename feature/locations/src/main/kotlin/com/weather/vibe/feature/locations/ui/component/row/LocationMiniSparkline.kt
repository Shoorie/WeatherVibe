package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.locations.ui.LocationsDefaults
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
      width = LocationsDefaults.MiniSparklineWidth,
      height = LocationsDefaults.MiniSparklineHeight
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
        width = LocationsDefaults.MiniSparklineStroke.toPx(),
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )
  }
}

private fun buildSmoothPath(offsets: List<Offset>): Path = Path().apply {
  if (offsets.isEmpty()) return@apply
  moveTo(offsets.first().x, offsets.first().y)
  for (index in 0 until offsets.size - 1) {
    val previous = offsets.getOrElse(index - 1) { offsets.first() }
    val current = offsets[index]
    val next = offsets[index + 1]
    val after = offsets.getOrElse(index + 2) { offsets.last() }
    val controlStart = Offset(
      x = current.x + (next.x - previous.x) * SMOOTHING,
      y = current.y + (next.y - previous.y) * SMOOTHING
    )
    val controlEnd = Offset(
      x = next.x - (after.x - current.x) * SMOOTHING,
      y = next.y - (after.y - current.y) * SMOOTHING
    )
    cubicTo(
      controlStart.x, controlStart.y,
      controlEnd.x, controlEnd.y,
      next.x, next.y
    )
  }
}

private const val SMOOTHING: Float = 0.16f

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationMiniSparkline(
      points = persistentListOf(3f, 5f, 8f, 11f, 12f, 10f, 7f, 4f, 2f)
    )
  }
}
