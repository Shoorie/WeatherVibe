package com.weather.vibe.feature.widget.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class WidgetMessage(
  val body: String,
  val emoji: String,
  val title: String
)
