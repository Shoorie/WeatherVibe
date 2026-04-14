package com.weather.vibe.feature.widget.presentation.state

data class WidgetNoLocationUiState(
  val body: String,
  val emoji: String,
  val title: String
) : WidgetUiState
