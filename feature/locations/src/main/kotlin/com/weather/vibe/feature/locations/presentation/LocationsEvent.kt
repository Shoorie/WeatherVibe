package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot

internal sealed interface LocationsEvent {

  data class ShowRemovedSnackbar(
    val locationName: String,
    val location: Location,
    val label: String?,
    val snapshot: LocationWeatherSnapshot?
  ) : LocationsEvent

  data object NavigateToSearch : LocationsEvent
  data object ShowLimitReachedSnackbar : LocationsEvent
}
