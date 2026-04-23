package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot

internal sealed interface LocationsAction {

  data class OpenLocationDetails(
    val favoriteId: Long
  ) : LocationsAction

  data class RemoveLocationFavoriteClick(
    val favoriteId: Long
  ) : LocationsAction

  data class RenameLocationFavoriteClick(
    val favoriteId: Long,
    val label: String?
  ) : LocationsAction

  data class UndoRemoveLocationFavoriteClick(
    val location: Location,
    val label: String?,
    val snapshot: LocationWeatherSnapshot?
  ) : LocationsAction

  data object AddLocationClick : LocationsAction
  data object ExitCompareMode : LocationsAction
  data object Initialize : LocationsAction
  data object PullToRefresh : LocationsAction
  data object ToggleCompareMode : LocationsAction
}
