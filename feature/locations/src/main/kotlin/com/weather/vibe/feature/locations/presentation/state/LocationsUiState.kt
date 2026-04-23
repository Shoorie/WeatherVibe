package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.locations.ui.LocationsDefaults.SelectionLimit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet

internal sealed interface LocationsUiState {

  @Immutable
  data object Loading : LocationsUiState

  @Immutable
  data class Loaded(
    val cards: ImmutableList<LocationCardUiState>,
    val comparePair: LocationComparePairUiState?,
    val compareMode: Boolean,
    val isRefreshing: Boolean,
    val selectedIds: ImmutableSet<Long>
  ) : LocationsUiState {

    @Stable
    val hasEnoughCardsForCompare: Boolean
      get() = cards.size >= SelectionLimit

    @Stable
    val canAddMoreFavorites: Boolean
      get() = cards.size < MAX_FAVORITES

    @Stable
    fun isCardSelected(favoriteId: Long): Boolean =
      favoriteId in selectedIds

    @Stable
    fun isCardLocked(favoriteId: Long): Boolean =
      compareMode && selectedIds.size >= SelectionLimit && favoriteId !in selectedIds

    @Stable
    fun withRefreshing(isRefreshing: Boolean): Loaded =
      copy(isRefreshing = isRefreshing)

    @Stable
    fun withToggledCompareMode(): Loaded =
      copy(
        compareMode = !compareMode,
        comparePair = null,
        selectedIds = persistentSetOf()
      )

    @Stable
    fun withSelectionCleared(): Loaded =
      copy(
        selectedIds = persistentSetOf(),
        comparePair = null
      )

    @Stable
    fun withToggledSelection(favoriteId: Long): Loaded {
      val next = when {
        favoriteId in selectedIds -> (selectedIds - favoriteId).toPersistentSet()
        selectedIds.size >= SelectionLimit -> selectedIds
        else -> (selectedIds + favoriteId).toPersistentSet()
      }
      return copy(selectedIds = next)
    }

    companion object {
      fun emptyFor(cards: ImmutableList<LocationCardUiState>): Loaded =
        Loaded(
          cards = cards,
          comparePair = null,
          compareMode = false,
          isRefreshing = false,
          selectedIds = persistentSetOf()
        )
    }
  }

  @Immutable
  data class Error(val message: String) : LocationsUiState
}
