package com.weather.vibe.feature.settings.ui.component

import androidx.compose.runtime.Immutable

@Immutable
internal data class SettingsToggle(
  val checked: Boolean,
  val onChange: (Boolean) -> Unit,
  val stateLabel: String
)
