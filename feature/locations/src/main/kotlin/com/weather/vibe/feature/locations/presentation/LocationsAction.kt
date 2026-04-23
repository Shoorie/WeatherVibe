package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.model.Location

sealed interface LocationsAction {
  data object AddCityClick : LocationsAction
  data class CardClick(val cardId: String) : LocationsAction
  data object CloseCompare : LocationsAction
  data object Initialize : LocationsAction
  data object RefreshClick : LocationsAction
  data class RemoveClick(val cardId: String) : LocationsAction
  data class RenameClick(val cardId: String, val label: String?) : LocationsAction
  data object ToggleCompareMode : LocationsAction
  data class UndoRemoveClick(val location: Location, val label: String?) : LocationsAction
}
