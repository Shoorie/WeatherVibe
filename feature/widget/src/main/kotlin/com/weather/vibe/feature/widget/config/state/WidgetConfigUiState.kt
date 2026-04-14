package com.weather.vibe.feature.widget.config.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface WidgetConfigUiState {

  @Immutable
  data object Loading : WidgetConfigUiState

  @Immutable
  data class Ready(val locations: List<LocationPickerItemUiState>) : WidgetConfigUiState

  @Immutable
  data class Empty(val hint: String) : WidgetConfigUiState

  @Immutable
  data class Error(val message: String) : WidgetConfigUiState
}
