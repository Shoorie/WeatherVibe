package com.weather.vibe.feature.widget.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
sealed interface WidgetMessageUiState : WidgetUiState {
  val body: String
  val emoji: String
  val title: String
}
