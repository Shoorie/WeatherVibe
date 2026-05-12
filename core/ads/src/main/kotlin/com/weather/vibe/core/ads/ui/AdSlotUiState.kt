package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Immutable

@Immutable
data class AdSlotUiState(
  val adUnitId: String,
  val isVisible: Boolean
) {

  companion object {
    val Hidden = AdSlotUiState(
      adUnitId = "",
      isVisible = false
    )
  }
}
