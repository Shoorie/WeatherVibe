package com.weather.vibe.feature.widget.presentation.state

data class WidgetReadyUiState(
  val conditionEmoji: String,
  val contentDescription: String,
  val locationId: Long,
  val locationName: String,
  val mood: String,
  val temperature: String,
  val vibeText: String
) : WidgetUiState
