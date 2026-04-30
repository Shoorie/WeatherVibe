package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.SUN_ROTATION
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.SUN_ROTATION_ANGLE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.SUN_ROTATION_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.SUN_ROTATION_FULL_TURN
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.SunIconSize
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SpinningSunIcon(modifier: Modifier = Modifier) {

  val angle by rememberRotationAngle()
  val color = colors.colorWarm

  Canvas(modifier = modifier.size(SunIconSize)) {
    rotate(degrees = angle, pivot = center) {
      drawSun(color = color)
    }
  }
}

@Composable
private fun rememberRotationAngle() =
  rememberInfiniteTransition(label = SUN_ROTATION).animateFloat(
    initialValue = 0f,
    targetValue = SUN_ROTATION_FULL_TURN,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = SUN_ROTATION_DURATION_MS, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = SUN_ROTATION_ANGLE
  )

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawSun(
  color: androidx.compose.ui.graphics.Color
) {

  val coreRadius = size.minDimension * CORE_RADIUS_RATIO
  val rayInner = size.minDimension * RAY_INNER_RATIO
  val rayOuter = size.minDimension * RAY_OUTER_RATIO
  val strokeStyle = Stroke(width = size.minDimension * RAY_STROKE_RATIO, cap = StrokeCap.Round)

  drawCircle(color = color, radius = coreRadius, center = center)
  repeat(RAY_COUNT) { index ->
    val angleRadians = index * RAY_STEP_RADIANS
    val cosAngle = cos(angleRadians)
    val sinAngle = sin(angleRadians)
    drawLine(
      color = color,
      start = Offset(center.x + cosAngle * rayInner, center.y + sinAngle * rayInner),
      end = Offset(center.x + cosAngle * rayOuter, center.y + sinAngle * rayOuter),
      strokeWidth = strokeStyle.width,
      cap = strokeStyle.cap
    )
  }
}

private const val RAY_COUNT = 8
private const val RAY_STEP_RADIANS = (2.0 * Math.PI / RAY_COUNT).toFloat()
private const val CORE_RADIUS_RATIO = 0.22f
private const val RAY_INNER_RATIO = 0.32f
private const val RAY_OUTER_RATIO = 0.46f
private const val RAY_STROKE_RATIO = 0.07f

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SpinningSunIcon()
  }
}
