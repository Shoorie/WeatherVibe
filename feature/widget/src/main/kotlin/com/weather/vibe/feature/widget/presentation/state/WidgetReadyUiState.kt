package com.weather.vibe.feature.widget.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class WidgetReadyUiState(
  val conditionEmoji: String,
  val conditionLabel: String,
  val contentDescription: String,
  val fetchedAtLabel: String,
  val locationId: Long,
  val locationName: String,
  val mood: String,
  val temperature: String
) : WidgetUiState
