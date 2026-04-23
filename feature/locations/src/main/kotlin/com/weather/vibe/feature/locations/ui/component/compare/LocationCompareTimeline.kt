package com.weather.vibe.feature.locations.ui.component.compare

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUi
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.timelineTitle
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.valueTemperature
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.roundToInt

@Composable
internal fun LocationCompareTimeline(
  modifier: Modifier = Modifier,
  first: LocationCompareUi,
  second: LocationCompareUi
) {
  val firstColor = colors.accent
  val secondColor = colors.colorWarm
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.glassSurface)
      .border(
        width = Border,
        color = colors.outlineVariant,
        shape = shapes.card
      )
      .padding(Medium),
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      text = timelineTitle(),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
    TimelineChart(
      firstPoints = first.hourlyTemperatures,
      firstColor = firstColor,
      secondPoints = second.hourlyTemperatures,
      secondColor = secondColor
    )
    TimelineLegend(
      firstName = first.card.name,
      firstColor = firstColor,
      secondName = second.card.name,
      secondColor = secondColor
    )
  }
}

@Composable
private fun TimelineChart(
  firstPoints: ImmutableList<Float>,
  firstColor: Color,
  secondPoints: ImmutableList<Float>,
  secondColor: Color
) {
  if (firstPoints.isEmpty() || secondPoints.isEmpty()) return
  val bounds = remember(firstPoints, secondPoints) {
    temperatureBounds(first = firstPoints, second = secondPoints)
  }
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(LocationsDefaults.TimelineHeight),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    TimelineYAxis(
      minValue = bounds.min,
      maxValue = bounds.max
    )
    TimelineCanvas(
      modifier = Modifier.weight(1f),
      firstPoints = firstPoints,
      firstColor = firstColor,
      secondPoints = secondPoints,
      secondColor = secondColor,
      minValue = bounds.min,
      maxValue = bounds.max
    )
  }
}

private data class TemperatureBounds(val min: Float, val max: Float)

private fun temperatureBounds(
  first: ImmutableList<Float>,
  second: ImmutableList<Float>
): TemperatureBounds = TemperatureBounds(
  min = minOf(first.min(), second.min()) - AXIS_PADDING,
  max = maxOf(first.max(), second.max()) + AXIS_PADDING
)

private const val AXIS_PADDING: Float = 1f
private const val GRID_LINE_COUNT: Int = 4

@Composable
private fun TimelineYAxis(
  minValue: Float,
  maxValue: Float
) {
  val midValue = (minValue + maxValue) / 2f
  Column(
    modifier = Modifier.fillMaxHeight(),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.End
  ) {
    AxisLabel(temperatureC = maxValue.roundToInt())
    AxisLabel(temperatureC = midValue.roundToInt())
    AxisLabel(temperatureC = minValue.roundToInt())
  }
}

@Composable
private fun AxisLabel(temperatureC: Int) {
  Text(
    text = valueTemperature(value = temperatureC),
    style = typography.labelSmall,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun TimelineCanvas(
  modifier: Modifier,
  firstPoints: ImmutableList<Float>,
  firstColor: Color,
  secondPoints: ImmutableList<Float>,
  secondColor: Color,
  minValue: Float,
  maxValue: Float
) {
  val gridlineColor = colors.outlineVariant
  Canvas(modifier = modifier.fillMaxHeight()) {
    val range = (maxValue - minValue).takeIf { it > 0f } ?: 1f
    drawHorizontalGrid(color = gridlineColor)
    drawCurve(
      points = firstPoints,
      color = firstColor,
      minValue = minValue,
      range = range,
      strokeWidthPx = LocationsDefaults.TimelineStrokeWidth.toPx(),
      endDotRadiusPx = LocationsDefaults.TimelineDotRadius.toPx()
    )
    drawCurve(
      points = secondPoints,
      color = secondColor,
      minValue = minValue,
      range = range,
      strokeWidthPx = LocationsDefaults.TimelineStrokeWidth.toPx(),
      endDotRadiusPx = LocationsDefaults.TimelineDotRadius.toPx()
    )
  }
}

private fun DrawScope.drawHorizontalGrid(color: Color) {
  for (index in 0..GRID_LINE_COUNT) {
    val y = size.height * index / GRID_LINE_COUNT
    drawLine(
      color = color,
      start = Offset(x = 0f, y = y),
      end = Offset(x = size.width, y = y),
      strokeWidth = 1f
    )
  }
}

private fun DrawScope.drawCurve(
  points: ImmutableList<Float>,
  color: Color,
  minValue: Float,
  range: Float,
  strokeWidthPx: Float,
  endDotRadiusPx: Float
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
      width = strokeWidthPx,
      cap = StrokeCap.Round,
      join = StrokeJoin.Round
    )
  )
  drawCircle(
    color = color,
    radius = endDotRadiusPx,
    center = offsets.last()
  )
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

@Composable
private fun TimelineLegend(
  firstName: String,
  firstColor: Color,
  secondName: String,
  secondColor: Color
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    LegendDot(
      name = firstName,
      color = firstColor
    )
    LegendDot(
      name = secondName,
      color = secondColor
    )
  }
}

@Composable
private fun LegendDot(
  name: String,
  color: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Box(
      modifier = Modifier
        .size(LocationsDefaults.TimelineDotRadius * 2)
        .clip(CircleShape)
        .background(color)
    )
    Text(
      text = name,
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationCompareTimeline(
      first = LocationsPreviewData.warsawCompare,
      second = LocationsPreviewData.madridCompare
    )
  }
}
