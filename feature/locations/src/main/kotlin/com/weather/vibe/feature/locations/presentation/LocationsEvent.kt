package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.model.Location

sealed interface LocationsEvent {

  data class ShowRemovedSnackbar(
    val locationName: String,
    val location: Location,
    val label: String?
  ) : LocationsEvent

  data object NavigateToSearch : LocationsEvent
  data object ShowLimitReachedSnackbar : LocationsEvent
}
