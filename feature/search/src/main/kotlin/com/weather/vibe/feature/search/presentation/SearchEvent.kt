package com.weather.vibe.feature.search.presentation

internal sealed interface SearchEvent {
  data class NavigateBackWithResult(
    val cityName: String,
    val latitude: Double,
    val longitude: Double
  ) : SearchEvent

  data object NavigateBack : SearchEvent
}
