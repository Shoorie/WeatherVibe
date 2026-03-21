package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.LocationResult

internal sealed interface HomeAction {
  data object RefreshClick : HomeAction
  data object ToggleSearch : HomeAction
  data object DismissSearch : HomeAction
  data class QueryChange(val query: String) : HomeAction
  data class LocationSelect(val result: LocationResult) : HomeAction
}
