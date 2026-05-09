package com.weather.vibe.feature.home.presentation.widgetpromo

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface WidgetPromoUiState {

  @Immutable
  data object Pending : WidgetPromoUiState

  @Immutable
  data object Hidden : WidgetPromoUiState

  @Immutable
  data object Visible : WidgetPromoUiState
}
