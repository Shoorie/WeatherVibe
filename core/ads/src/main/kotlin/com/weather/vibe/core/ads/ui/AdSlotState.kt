package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AdSlotState(
  val adUnitId: String,
  val bottomInset: Dp,
  val isVisible: Boolean
) {

  companion object {
    val Hidden = AdSlotState(
      adUnitId = "",
      bottomInset = 0.dp,
      isVisible = false
    )
  }
}
