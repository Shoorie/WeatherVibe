package com.weather.vibe.feature.search.presentation

internal sealed interface SearchAction {
  data object BackClick : SearchAction
  data class LocationSelect(val id: Long) : SearchAction
  data class HeartClick(val id: Long) : SearchAction
  data class PermissionResult(val canAskAgain: Boolean, val granted: Boolean) : SearchAction
  data class SetMode(val mode: SearchMode) : SearchAction
  data class QueryChange(val query: String) : SearchAction
  data object Retry : SearchAction
  data object UseMyLocationClick : SearchAction
}
