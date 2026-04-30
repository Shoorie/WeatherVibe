package com.weather.vibe.feature.onboarding.ui.screen.welcome

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.STAGGER_ENTRY
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.STAGGER_POP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.DecelerateExpressive
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.FADE_UP_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.FADE_UP_OFFSET_DP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.OvershootSoft
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.POP_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.RISE_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.RISE_OFFSET_DP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.SLIDE_RIGHT_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.SLIDE_RIGHT_OFFSET_DP

@Composable
internal fun Modifier.staggeredFadeUp(
  delayMs: Int,
  durationMs: Int = FADE_UP_DURATION_MS,
  offset: Dp = FADE_UP_OFFSET_DP.dp
): Modifier {

  val progress = rememberEntryProgress(
    enabled = true,
    delayMs = delayMs,
    durationMs = durationMs
  )
  val offsetPx = LocalDensity.current.toPx(offset)

  return alpha(progress)
    .graphicsLayer { translationY = offsetPx * (1f - progress) }
}

@Composable
internal fun Modifier.staggeredRise(
  enabled: Boolean = true,
  delayMs: Int,
  durationMs: Int = RISE_DURATION_MS,
  offset: Dp = RISE_OFFSET_DP.dp,
  scaleFrom: Float = 0.94f
): Modifier {

  val progress = rememberEntryProgress(
    enabled = enabled,
    delayMs = delayMs,
    durationMs = durationMs
  )
  val offsetPx = LocalDensity.current.toPx(offset)

  return alpha(progress).graphicsLayer {
    translationY = offsetPx * (1f - progress)
    val current = scaleFrom + (1f - scaleFrom) * progress
    scaleX = current
    scaleY = current
  }
}

@Composable
internal fun Modifier.staggeredSlideRight(
  enabled: Boolean = true,
  delayMs: Int,
  durationMs: Int = SLIDE_RIGHT_DURATION_MS,
  offset: Dp = SLIDE_RIGHT_OFFSET_DP.dp
): Modifier {

  val progress = rememberEntryProgress(
    enabled = enabled,
    delayMs = delayMs,
    durationMs = durationMs
  )
  val offsetPx = LocalDensity.current.toPx(offset)

  return graphicsLayer { translationX = offsetPx * (1f - progress) }
}

@Composable
internal fun Modifier.staggeredPop(
  enabled: Boolean = true,
  delayMs: Int,
  durationMs: Int = POP_DURATION_MS,
  scaleFrom: Float = 0.6f
): Modifier {

  val progress = rememberPopProgress(
    enabled = enabled,
    delayMs = delayMs,
    durationMs = durationMs
  )

  return alpha(progress.coerceIn(0f, 1f)).graphicsLayer {
    val current = scaleFrom + (1f - scaleFrom) * progress
    scaleX = current
    scaleY = current
  }
}

@Composable
private fun rememberEntryProgress(enabled: Boolean, delayMs: Int, durationMs: Int): Float {

  var visible by remember { mutableStateOf(false) }
  LaunchedEffect(enabled) {
    if (enabled) visible = true
  }

  val progress by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = durationMs,
      delayMillis = delayMs,
      easing = DecelerateExpressive
    ),
    label = STAGGER_ENTRY
  )
  return progress
}

@Composable
private fun rememberPopProgress(enabled: Boolean, delayMs: Int, durationMs: Int): Float {

  var visible by remember { mutableStateOf(false) }
  LaunchedEffect(enabled) {
    if (enabled) visible = true
  }

  val progress by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = durationMs,
      delayMillis = delayMs,
      easing = OvershootSoft
    ),
    label = STAGGER_POP
  )
  return progress
}

private fun Density.toPx(dp: Dp): Float = with(this) { dp.toPx() }
