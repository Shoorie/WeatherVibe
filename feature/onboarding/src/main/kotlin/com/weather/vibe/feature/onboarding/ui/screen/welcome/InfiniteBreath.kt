package com.weather.vibe.feature.onboarding.ui.screen.welcome

import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.BREATHING_SCALE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.DecelerateExpressive

@Composable
internal fun rememberBreathingScale(
  durationMs: Int,
  minScale: Float = 1f,
  maxScale: Float = 1.04f,
  label: String = BREATHING_SCALE
): State<Float> {

  val transition = rememberInfiniteTransition(label = label)
  return transition.animateFloat(
    initialValue = minScale,
    targetValue = maxScale,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMs,
        easing = DecelerateExpressive
      ),
      repeatMode = Reverse
    ),
    label = label
  )
}
