package com.weather.vibe.feature.widget.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class WidgetWaitingUiState(
  override val body: String,
  override val emoji: String,
  override val title: String
) : WidgetMessageUiState
