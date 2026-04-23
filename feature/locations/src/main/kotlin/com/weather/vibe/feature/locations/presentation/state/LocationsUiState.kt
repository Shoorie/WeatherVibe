package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

sealed interface LocationsUiState {

  @Immutable
  data object Loading : LocationsUiState

  @Immutable
  data class Loaded(
    val cards: ImmutableList<LocationCardUi>,
    val comparePair: LocationComparePair?,
    val compareMode: Boolean,
    val isRefreshing: Boolean,
    val selectedIds: ImmutableSet<String>
  ) : LocationsUiState {

    companion object {

      fun emptyFor(cards: ImmutableList<LocationCardUi>): Loaded =
        Loaded(
          cards = cards,
          comparePair = null,
          compareMode = false,
          isRefreshing = false,
          selectedIds = persistentSetOf()
        )

      val EMPTY: Loaded = emptyFor(cards = persistentListOf())
    }
  }

  @Immutable
  data class Error(val message: String) : LocationsUiState
}
