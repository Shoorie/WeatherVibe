package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class HomeAlertUiState(
  val indicator: String,
  val title: String,
  val message: String,
  val contentDescription: String
)
