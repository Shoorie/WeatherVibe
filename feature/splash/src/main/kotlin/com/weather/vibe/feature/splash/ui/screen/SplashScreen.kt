package com.weather.vibe.feature.splash.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.splash.ui.SplashResources.Texts.appName
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.ExitDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.HoldDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.TextYOffset
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  modifier: Modifier = Modifier,
  onNavigateToHome: () -> Unit
) {

  val anim = remember { SplashAnimatables() }
  val gradientStart = colors.backgroundGradientStart
  val gradientEnd = colors.backgroundGradientEnd
  val backgroundBrush = remember(gradientStart, gradientEnd) {
    Brush.verticalGradient(colors = listOf(gradientStart, gradientEnd))
  }

  LaunchedEffect(Unit) {
    glowBurstsIn(anim = anim)
    ringsExpandSequentially(anim = anim)
    delay(HoldDuration)
    titleSlidesIn(anim = anim)
    delay(HoldDuration)
    screenFadesOut(anim = anim)
    delay(ExitDuration.toLong())
    onNavigateToHome()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(brush = backgroundBrush)
      .graphicsLayer {
        alpha = anim.exitAlpha.value
        scaleX = anim.exitScale.value
        scaleY = anim.exitScale.value
      },
    contentAlignment = Alignment.Center
  ) {
    SplashGlowCanvas(anim = anim)
    SplashAppName(
      alpha = anim.textAlpha.value,
      yOffset = anim.textSlide.value
    )
  }
}

@Composable
private fun SplashGlowCanvas(
  modifier: Modifier = Modifier,
  anim: SplashAnimatables
) {
  val accentColor = colors.accent
  Canvas(
    modifier = modifier
      .fillMaxSize()
      .graphicsLayer { alpha = anim.glowAlpha.value }
  ) {
    val center = Offset(
      x = size.width / 2f,
      y = size.height * SplashDefaults.GlowCenterYFraction
    )
    val baseRadius = size.minDimension *
      SplashDefaults.GlowRadiusFraction *
      anim.glowScale.value
    val ringStrokeWidth = SplashDefaults.RingStrokeDp.dp.toPx()
    drawGlowLayers(
      accentColor = accentColor,
      baseRadius = baseRadius,
      center = center
    )
    drawSunRays(
      baseRadius = baseRadius,
      center = center,
      glowScale = anim.glowScale.value,
      sunColor = SplashDefaults.SunYellow
    )
    drawAtmosphericRings(
      accentColor = accentColor,
      baseRadius = baseRadius,
      center = center,
      ring1Progress = anim.ring1Progress.value,
      ring2Progress = anim.ring2Progress.value,
      ring3Progress = anim.ring3Progress.value,
      ringStrokeWidth = ringStrokeWidth
    )
  }
}

@Composable
private fun SplashAppName(
  modifier: Modifier = Modifier,
  alpha: Float,
  yOffset: Float
) {
  Text(
    modifier = modifier.offset(y = TextYOffset + yOffset.dp),
    text = appName(),
    style = typography.displaySmall,
    color = colors.onBackground.copy(alpha = alpha)
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SplashScreen(onNavigateToHome = {})
  }
}
