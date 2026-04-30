package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.runtime.Immutable

@Immutable
internal data class ReadyParticleUiState(
  val delaySeconds: Float,
  val durationSeconds: Float,
  val leftFraction: Float,
  val opacity: Float,
  val sizeDp: Int
)
