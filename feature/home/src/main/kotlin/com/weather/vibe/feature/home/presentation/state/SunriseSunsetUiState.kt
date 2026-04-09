package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class SunriseSunsetUiState(
  val dayLength: String,
  val sunProgress: Float,
  val sunriseTime: String,
  val sunsetTime: String
) {

  companion object {
    val Empty = SunriseSunsetUiState(
      dayLength = "",
      sunProgress = 0f,
      sunriseTime = "",
      sunsetTime = ""
    )
  }
}
