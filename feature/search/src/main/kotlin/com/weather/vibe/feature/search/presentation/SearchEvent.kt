package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.Location

internal sealed interface SearchEvent {
  data class NavigateBackWithResult(val location: Location) : SearchEvent
  data object NavigateBack : SearchEvent
  data object LimitReached : SearchEvent
  data object OpenAppSettings : SearchEvent
  data object RequestLocationPermission : SearchEvent
}
