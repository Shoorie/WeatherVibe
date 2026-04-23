package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.location.model.WeatherComparison

@Immutable
data class LocationComparePair(
  val first: LocationCompareUi,
  val second: LocationCompareUi,
  val winners: WeatherComparison
)
