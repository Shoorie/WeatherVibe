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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUiState
import com.weather.vibe.feature.locations.presentation.state.TemperatureAxisUiState
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsDefaults.TimelineAxisLabelColumnWidth
import com.weather.vibe.feature.locations.ui.LocationsDefaults.TimelineAxisPadding
import com.weather.vibe.feature.locations.ui.LocationsDefaults.TimelineDotRadius
import com.weather.vibe.feature.locations.ui.LocationsDefaults.TimelineGridLineCount
import com.weather.vibe.feature.locations.ui.LocationsDefaults.TimelineStrokeWidth
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.timelineHours
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.timelineNow
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.timelineTitle
import com.weather.vibe.feature.locations.ui.util.buildSmoothPath
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun LocationCompareTemperatureChart(
  modifier: Modifier = Modifier,
  first: LocationCompareUiState,
  second: LocationCompareUiState,
  axis: TemperatureAxisUiState
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
    TemperatureChartBody(
      firstPoints = first.hourlyTemperatures,
      firstColor = firstColor,
      secondPoints = second.hourlyTemperatures,
      secondColor = secondColor,
      axis = axis
    )
    TemperatureChartXAxis()
    TemperatureChartLegend(
      firstName = first.card.name,
      firstColor = firstColor,
      secondName = second.card.name,
      secondColor = secondColor
    )
  }
}

@Composable
private fun TemperatureChartBody(
  firstPoints: ImmutableList<Float>,
  firstColor: Color,
  secondPoints: ImmutableList<Float>,
  secondColor: Color,
  axis: TemperatureAxisUiState
) {
  if (firstPoints.isEmpty() || secondPoints.isEmpty()) return
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(LocationsDefaults.TimelineHeight),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    TemperatureChartYAxis(axis = axis)
    TemperatureChartCanvas(
      modifier = Modifier.weight(1f),
      firstPoints = firstPoints,
      firstColor = firstColor,
      secondPoints = secondPoints,
      secondColor = secondColor
    )
  }
}

@Composable
private fun TemperatureChartYAxis(axis: TemperatureAxisUiState) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .width(TimelineAxisLabelColumnWidth),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.End
  ) {
    AxisLabel(value = axis.max)
    AxisLabel(value = axis.mid)
    AxisLabel(value = axis.min)
  }
}

@Composable
private fun TemperatureChartXAxis() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Box(modifier = Modifier.width(TimelineAxisLabelColumnWidth))
    Row(
      modifier = Modifier.weight(1f),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      AxisLabel(value = timelineNow())
      AxisLabel(value = timelineHours(offset = 6))
      AxisLabel(value = timelineHours(offset = 12))
      AxisLabel(value = timelineHours(offset = 18))
      AxisLabel(value = timelineHours(offset = 24))
    }
  }
}

@Composable
private fun AxisLabel(value: String) {
  Text(
    text = value,
    style = typography.labelSmall,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun TemperatureChartCanvas(
  modifier: Modifier,
  firstPoints: ImmutableList<Float>,
  firstColor: Color,
  secondPoints: ImmutableList<Float>,
  secondColor: Color
) {
  val gridlineColor = colors.outlineVariant
  Canvas(modifier = modifier.fillMaxHeight()) {
    val combinedMin = minOf(firstPoints.min(), secondPoints.min()) - TimelineAxisPadding
    val combinedMax = maxOf(firstPoints.max(), secondPoints.max()) + TimelineAxisPadding
    val range = (combinedMax - combinedMin).takeIf { it > 0f } ?: 1f
    drawHorizontalGrid(color = gridlineColor)
    drawCurve(
      points = firstPoints,
      color = firstColor,
      minValue = combinedMin,
      range = range,
      strokeWidthPx = TimelineStrokeWidth.toPx(),
      endDotRadiusPx = TimelineDotRadius.toPx()
    )
    drawCurve(
      points = secondPoints,
      color = secondColor,
      minValue = combinedMin,
      range = range,
      strokeWidthPx = TimelineStrokeWidth.toPx(),
      endDotRadiusPx = TimelineDotRadius.toPx()
    )
  }
}

private fun DrawScope.drawHorizontalGrid(color: Color) {
  val gridLineCount = TimelineGridLineCount
  for (index in 0..gridLineCount) {
    val y = size.height * index / gridLineCount
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

@Composable
private fun TemperatureChartLegend(
  firstName: String,
  firstColor: Color,
  secondName: String,
  secondColor: Color
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    LegendDot(name = firstName, color = firstColor)
    LegendDot(name = secondName, color = secondColor)
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
        .size(TimelineDotRadius * 2)
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
    LocationCompareTemperatureChart(
      first = LocationsPreviewData.londonCompare,
      second = LocationsPreviewData.madridCompare,
      axis = LocationsPreviewData.comparePair.temperatureAxis
    )
  }
}
