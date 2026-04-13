package com.weather.vibe.feature.home.ui.component.sun

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.ui.HomeDefaults.SunArcHeight
import com.weather.vibe.feature.home.ui.HomeDefaults.SunArcStrokeWidth
import com.weather.vibe.feature.home.ui.HomeDefaults.SunDotRadius
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val ARC_PADDING_RATIO = 0.08f
private const val ARC_SWEEP = 180f
private const val GLOW_ALPHA = 0.3f
private const val GLOW_MULTIPLIER = 2.5f

@Composable
internal fun SunArcCanvas(
  modifier: Modifier = Modifier,
  sunProgress: Float
) {

  val trackColor = colors.outline
  val accentColor = colors.accent
  val accentDarkColor = colors.accentDark
  val glowColor = colors.accent.copy(alpha = GLOW_ALPHA)
  val gradientBrush = remember(accentColor, accentDarkColor) {
    Brush.linearGradient(colors = listOf(accentColor, accentDarkColor))
  }

  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(SunArcHeight)
  ) {
    val padding = ARC_PADDING_RATIO * size.width
    val strokeWidth = SunArcStrokeWidth.toPx()
    val dotRadius = SunDotRadius.toPx()
    val arcWidth = size.width - padding * 2
    val arcHeight = size.height - dotRadius * 2
    val arcTopLeft = Offset(padding, dotRadius)
    val arcSize = Size(arcWidth, arcHeight * 2)
    val arcStroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

    drawArc(
      color = trackColor,
      startAngle = 180f,
      sweepAngle = 180f,
      useCenter = false,
      topLeft = arcTopLeft,
      size = arcSize,
      style = arcStroke
    )

    if (sunProgress > 0f) {
      drawArc(
        brush = gradientBrush,
        startAngle = 180f,
        sweepAngle = ARC_SWEEP * sunProgress,
        useCenter = false,
        topLeft = arcTopLeft,
        size = arcSize,
        style = arcStroke
      )
    }

    drawSunDot(
      sunProgress = sunProgress,
      accentColor = accentColor,
      glowColor = glowColor,
      arcLeft = padding,
      arcWidth = arcWidth,
      arcHeight = arcHeight,
      dotRadius = dotRadius
    )
  }
}

private fun DrawScope.drawSunDot(
  sunProgress: Float,
  accentColor: Color,
  glowColor: Color,
  arcLeft: Float,
  arcWidth: Float,
  arcHeight: Float,
  dotRadius: Float
) {

  val angle = PI + PI * sunProgress
  val centerX = arcLeft + arcWidth / 2
  val centerY = dotRadius + arcHeight
  val dotCenter = Offset(
    x = centerX + (arcWidth / 2) * cos(angle).toFloat(),
    y = centerY + arcHeight * sin(angle).toFloat()
  )

  drawCircle(
    color = glowColor,
    radius = dotRadius * GLOW_MULTIPLIER,
    center = dotCenter
  )
  drawCircle(
    color = accentColor,
    radius = dotRadius,
    center = dotCenter
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SunArcCanvas(
      modifier = Modifier.padding(Medium),
      sunProgress = 0.65f
    )
  }
}
