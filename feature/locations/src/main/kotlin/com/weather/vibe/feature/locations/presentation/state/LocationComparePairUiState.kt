package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.location.model.LocationWeatherComparison

@Immutable
internal data class LocationComparePairUiState(
  val first: LocationCompareUiState,
  val second: LocationCompareUiState,
  val winners: LocationWeatherComparison,
  val temperatureAxis: TemperatureAxisUiState
)
