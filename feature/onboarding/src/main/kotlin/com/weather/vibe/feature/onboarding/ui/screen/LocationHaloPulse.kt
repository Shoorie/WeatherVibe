package com.weather.vibe.feature.onboarding.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HALO_ALPHA_INNER
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HALO_ALPHA_MID
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HALO_ALPHA_OUTER
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HALO_PULSE_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HaloDiameter
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.INTENSITY_IDLE
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.PIN_ALPHA_MAX
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.PIN_ALPHA_MIN
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.PIN_SCALE_MAX
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.PIN_SCALE_MIN
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.PinSize

@Composable
internal fun LocationHaloPulse(
  modifier: Modifier = Modifier,
  contentDescription: String,
  intensity: Float = INTENSITY_IDLE
) {

  val haloBrush = rememberHaloBrush(
    tint = colors.accent,
    intensity = intensity
  )
  val pinTransform = rememberPinTransform()

  Box(
    modifier = modifier
      .size(HaloDiameter)
      .semantics { this.contentDescription = contentDescription },
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(HaloDiameter)
        .clip(CircleShape)
        .background(haloBrush)
    )
    Icon(
      imageVector = Icons.Filled.LocationOn,
      contentDescription = null,
      tint = colors.accent,
      modifier = Modifier
        .size(PinSize)
        .graphicsLayer {
          scaleX = pinTransform.scale
          scaleY = pinTransform.scale
          alpha = pinTransform.alpha
        }
    )
  }
}

@Composable
private fun rememberHaloBrush(tint: Color, intensity: Float): Brush =
  remember(tint, intensity) {
    Brush.radialGradient(
      colors = listOf(
        tint.copy(alpha = HALO_ALPHA_INNER * intensity),
        tint.copy(alpha = HALO_ALPHA_MID * intensity),
        tint.copy(alpha = HALO_ALPHA_OUTER * intensity),
        Color.Transparent
      )
    )
  }

@Composable
private fun rememberPinTransform(): PinTransform {
  val transition = rememberInfiniteTransition(label = "LocationHaloPulse")
  val pulseSpec = remember {
    infiniteRepeatable<Float>(
      animation = tween(
        durationMillis = HALO_PULSE_DURATION_MS,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    )
  }
  val scale by transition.animateFloat(
    initialValue = PIN_SCALE_MIN,
    targetValue = PIN_SCALE_MAX,
    animationSpec = pulseSpec,
    label = "pin-scale"
  )
  val alpha by transition.animateFloat(
    initialValue = PIN_ALPHA_MIN,
    targetValue = PIN_ALPHA_MAX,
    animationSpec = pulseSpec,
    label = "pin-alpha"
  )
  return PinTransform(scale = scale, alpha = alpha)
}

private data class PinTransform(val scale: Float, val alpha: Float)

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(colors.backgroundGradientStart)
        .size(HaloDiameter),
      contentAlignment = Alignment.Center
    ) {
      LocationHaloPulse(contentDescription = "Location pulse")
    }
  }
}
