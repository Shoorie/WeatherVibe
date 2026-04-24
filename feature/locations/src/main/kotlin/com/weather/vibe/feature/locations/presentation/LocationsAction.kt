package com.weather.vibe.feature.locations.presentation

internal sealed interface LocationsAction {

  data class OpenLocationDetails(
    val favoriteId: Long
  ) : LocationsAction

  data class ReorderLocationFavorites(
    val orderedIds: List<Long>
  ) : LocationsAction

  data class RemoveLocationFavoriteClick(
    val favoriteId: Long
  ) : LocationsAction

  data class RenameLocationFavoriteClick(
    val favoriteId: Long,
    val label: String?
  ) : LocationsAction

  data object AddLocationClick : LocationsAction
  data object ExitCompareMode : LocationsAction
  data object Initialize : LocationsAction
  data object PullToRefresh : LocationsAction
  data object ToggleCompareMode : LocationsAction
  data object UndoRemoveLocationFavoriteClick : LocationsAction
}
