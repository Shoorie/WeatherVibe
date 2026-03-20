package com.weather.vibe.feature.home

import com.weather.vibe.domain.model.LocationResult

sealed interface HomeEvent {
    data object RefreshWeather : HomeEvent
    data object ToggleSearch : HomeEvent
    data object DismissSearch : HomeEvent
    data class SearchQueryChanged(val query: String) : HomeEvent
    data class LocationSelected(val result: LocationResult) : HomeEvent
}
