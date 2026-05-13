package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class AdSlotState internal constructor(internal val adUnitId: String) {

  internal var configVisible: Boolean by mutableStateOf(false)
  internal var isLoaded: Boolean by mutableStateOf(false)

  val isShown: Boolean
    get() = configVisible && isLoaded

  companion object {
    val Hidden: AdSlotState = AdSlotState(adUnitId = "")
  }
}
