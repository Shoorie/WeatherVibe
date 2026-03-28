package com.weather.vibe.feature.splash.ui.screen

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.CoreAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.CoreRadiusFraction
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.GlowInnerAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.GlowOuterAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.HaloAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.HaloRadiusMultiplier
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.MaxRingRadiusFraction
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RayAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RayCount
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RayGapFraction
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RayLengthFraction
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RayStrokeDp
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RingExpansion
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RingFadeInEnd
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RingMaxAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.SunYellow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal fun DrawScope.drawGlowLayers(
  accentColor: Color,
  baseRadius: Float,
  center: Offset
) {
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        accentColor.copy(alpha = HaloAlpha),
        accentColor.copy(alpha = 0f)
      ),
      center = center,
      radius = (baseRadius * HaloRadiusMultiplier)
        .coerceAtLeast(minimumValue = 1f)
    ),
    radius = baseRadius * HaloRadiusMultiplier,
    center = center
  )
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        accentColor.copy(alpha = GlowOuterAlpha),
        accentColor.copy(alpha = GlowInnerAlpha),
        accentColor.copy(alpha = 0f)
      ),
      center = center,
      radius = baseRadius.coerceAtLeast(minimumValue = 1f)
    ),
    radius = baseRadius,
    center = center
  )
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        Color.White.copy(alpha = CoreAlpha),
        SunYellow,
        SunYellow.copy(alpha = 0f)
      ),
      center = center,
      radius = (baseRadius * CoreRadiusFraction)
        .coerceAtLeast(minimumValue = 1f)
    ),
    radius = baseRadius * CoreRadiusFraction,
    center = center
  )
}

internal fun DrawScope.drawSunRays(
  baseRadius: Float,
  center: Offset,
  glowScale: Float,
  sunColor: Color
) {
  val rayGap = baseRadius * RayGapFraction
  val rayLength = baseRadius * RayLengthFraction
  val rayAlpha = glowScale * RayAlpha
  val strokeWidth = RayStrokeDp.dp.toPx()
  for (i in 0 until RayCount) {
    val angle = i * (2.0 * PI / RayCount).toFloat()
    val startDist = baseRadius + rayGap
    val endDist = baseRadius + rayGap + rayLength
    drawLine(
      color = sunColor.copy(alpha = rayAlpha),
      start = Offset(
        x = center.x + startDist * cos(angle),
        y = center.y + startDist * sin(angle)
      ),
      end = Offset(
        x = center.x + endDist * cos(angle),
        y = center.y + endDist * sin(angle)
      ),
      strokeWidth = strokeWidth,
      cap = StrokeCap.Round
    )
  }
}

internal fun DrawScope.drawAtmosphericRings(
  accentColor: Color,
  baseRadius: Float,
  center: Offset,
  ring1Progress: Float,
  ring2Progress: Float,
  ring3Progress: Float,
  ringStrokeWidth: Float
) {
  val maxRingRadius = size.minDimension * MaxRingRadiusFraction
  val ringStroke = Stroke(width = ringStrokeWidth)
  for (progress in arrayOf(ring1Progress, ring2Progress, ring3Progress)) {
    val ringAlpha = if (progress < RingFadeInEnd) {
      progress / RingFadeInEnd * RingMaxAlpha
    } else {
      (1f - progress) * RingMaxAlpha
    }
    drawCircle(
      color = accentColor.copy(alpha = ringAlpha),
      radius = (baseRadius + baseRadius * RingExpansion * progress)
        .coerceAtMost(maximumValue = maxRingRadius),
      center = center,
      style = ringStroke
    )
  }
}
