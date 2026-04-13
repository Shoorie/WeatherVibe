package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class HourlyForecastsUiState(
  val items: ImmutableList<HourlyForecastUiState>
)
