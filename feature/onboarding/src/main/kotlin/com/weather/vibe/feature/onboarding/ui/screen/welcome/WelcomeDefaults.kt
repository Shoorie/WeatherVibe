package com.weather.vibe.feature.onboarding.ui.screen.welcome

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.ui.unit.dp

internal object WelcomeDefaults {

  val DecelerateExpressive = CubicBezierEasing(a = 0.16f, b = 1f, c = 0.3f, d = 1f)
  val OvershootSoft = CubicBezierEasing(a = 0.34f, b = 1.56f, c = 0.64f, d = 1f)
  val SkipTopPadding = 60.dp
  val SkipEndPadding = 24.dp

  const val FADE_UP_DURATION_MS = 700
  const val POP_DURATION_MS = 600
  const val RISE_DURATION_MS = 700
  const val SLIDE_RIGHT_DURATION_MS = 600
  const val FADE_UP_OFFSET_DP = 12
  const val RISE_OFFSET_DP = 20
  const val SLIDE_RIGHT_OFFSET_DP = 40
}
