package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.runtime.Immutable

@Immutable
internal data class ReadyNotificationCardUiState(
  val body: String,
  val emoji: String,
  val showBell: Boolean,
  val title: String
)
