package com.weather.vibe.feature.widget.presentation.state

data class WidgetWaitingUiState(
  val body: String,
  val emoji: String,
  val title: String
) : WidgetUiState
