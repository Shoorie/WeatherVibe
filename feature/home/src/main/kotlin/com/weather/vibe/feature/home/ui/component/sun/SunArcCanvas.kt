package com.weather.vibe.feature.home.ui.component.sun

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
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
import com.weather.vibe.feature.home.ui.component.sun.SunArcAnimates.EnterAnimationSpec
import com.weather.vibe.feature.home.ui.component.sun.SunArcDefaults.ArcPaddingRatio
import com.weather.vibe.feature.home.ui.component.sun.SunArcDefaults.ArcStartAngleDegrees
import com.weather.vibe.feature.home.ui.component.sun.SunArcDefaults.ArcSweepDegrees
import com.weather.vibe.feature.home.ui.component.sun.SunArcDefaults.GlowAlpha
import com.weather.vibe.feature.home.ui.component.sun.SunArcDefaults.GlowRadiusMultiplier
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SunArcCanvas(
  modifier: Modifier = Modifier,
  sunProgress: Float
) {

  val animatedProgress by rememberAnimatedSunProgress(target = sunProgress)
  val palette = rememberSunArcPalette()

  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(SunArcHeight)
  ) {
    val arc = computeArcGeometry()
    drawArcTrack(geometry = arc, color = palette.track)
    drawArcProgress(geometry = arc, progress = animatedProgress, brush = palette.progress)
    drawSunDot(geometry = arc, progress = animatedProgress, palette = palette)
  }
}

@Composable
private fun rememberAnimatedSunProgress(target: Float): State<Float> {
  val animator = remember { Animatable(initialValue = 0f) }
  LaunchedEffect(target) {
    animator.animateTo(
      targetValue = target,
      animationSpec = EnterAnimationSpec
    )
  }
  return animator.asState()
}

@Composable
private fun rememberSunArcPalette(): SunArcPalette {

  val accent = colors.accent
  val accentDark = colors.accentDark
  val track = colors.outline

  return remember(accent, accentDark, track) {
    SunArcPalette(
      accent = accent,
      glow = accent.copy(alpha = GlowAlpha),
      progress = linearGradient(colors = listOf(accent, accentDark)),
      track = track
    )
  }
}

private fun DrawScope.computeArcGeometry(): SunArcGeometry {

  val padding = ArcPaddingRatio * size.width
  val dotRadius = SunDotRadius.toPx()
  val arcWidth = size.width - padding * 2
  val arcHeight = size.height - dotRadius * 2

  return SunArcGeometry(
    topLeft = Offset(x = padding, y = dotRadius),
    size = Size(width = arcWidth, height = arcHeight * 2),
    stroke = Stroke(width = SunArcStrokeWidth.toPx(), cap = StrokeCap.Round),
    leftEdgeX = padding,
    width = arcWidth,
    height = arcHeight,
    dotRadius = dotRadius
  )
}

private fun DrawScope.drawArcTrack(geometry: SunArcGeometry, color: Color) {
  drawArc(
    color = color,
    startAngle = ArcStartAngleDegrees,
    sweepAngle = ArcSweepDegrees,
    useCenter = false,
    topLeft = geometry.topLeft,
    size = geometry.size,
    style = geometry.stroke
  )
}

private fun DrawScope.drawArcProgress(
  geometry: SunArcGeometry,
  progress: Float,
  brush: Brush
) {
  if (progress <= 0f) return
  drawArc(
    brush = brush,
    startAngle = ArcStartAngleDegrees,
    sweepAngle = ArcSweepDegrees * progress,
    useCenter = false,
    topLeft = geometry.topLeft,
    size = geometry.size,
    style = geometry.stroke
  )
}

private fun DrawScope.drawSunDot(
  geometry: SunArcGeometry,
  progress: Float,
  palette: SunArcPalette
) {
  val center = sunDotCenter(geometry = geometry, progress = progress)
  drawCircle(
    color = palette.glow,
    radius = geometry.dotRadius * GlowRadiusMultiplier,
    center = center
  )
  drawCircle(
    color = palette.accent,
    radius = geometry.dotRadius,
    center = center
  )
}

private fun sunDotCenter(geometry: SunArcGeometry, progress: Float): Offset {

  val angle = PI + PI * progress
  val centerX = geometry.leftEdgeX + geometry.width / 2
  val centerY = geometry.dotRadius + geometry.height

  return Offset(
    x = centerX + (geometry.width / 2) * cos(angle).toFloat(),
    y = centerY + geometry.height * sin(angle).toFloat()
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
