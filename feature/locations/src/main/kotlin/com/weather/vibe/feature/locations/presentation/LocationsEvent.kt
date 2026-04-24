package com.weather.vibe.feature.locations.presentation

internal sealed interface LocationsEvent {

  data class ShowRemovedSnackbar(
    val locationName: String
  ) : LocationsEvent

  data object NavigateToSearch : LocationsEvent
  data object ShowErrorSnackbar : LocationsEvent
  data object ShowLimitReachedSnackbar : LocationsEvent
}
