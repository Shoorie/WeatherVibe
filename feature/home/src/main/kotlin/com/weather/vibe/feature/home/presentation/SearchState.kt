package com.weather.vibe.feature.home.presentation

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.weather.model.LocationResult

@Immutable
internal data class SearchState(
  val isActive: Boolean = false,
  val isSearching: Boolean = false,
  val query: String = "",
  val results: List<LocationResult> = emptyList()
)
