package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot

/**
 * Snapshot of everything the ViewModel needs to put a just-removed favorite back in its
 * original slot with its weather intact when the user taps "Undo".
 */
internal data class PendingRemoval(
  val location: Location,
  val label: String?,
  val snapshot: LocationWeatherSnapshot?,
  val removedFavoriteId: Long,
  val originalOrder: List<Long>
)
