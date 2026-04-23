package com.weather.vibe.feature.locations.presentation.state

import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.ui.LocationsDefaults.SelectionLimit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet

internal fun Loaded.withCards(cards: ImmutableList<LocationCardUiState>): Loaded =
  copy(cards = cards)

internal fun Loaded.withRefreshing(isRefreshing: Boolean): Loaded =
  copy(isRefreshing = isRefreshing)

internal fun Loaded.withComparePair(pair: LocationComparePairUiState?): Loaded =
  copy(comparePair = pair)

internal fun Loaded.withSelection(selectedFavoriteIds: ImmutableSet<Long>): Loaded =
  copy(selectedFavoriteIds = selectedFavoriteIds)

internal fun Loaded.withToggledCompareMode(): Loaded =
  copy(
    compareMode = !compareMode,
    comparePair = null,
    selectedFavoriteIds = persistentSetOf()
  )

internal fun Loaded.withSelectionCleared(): Loaded =
  copy(selectedFavoriteIds = persistentSetOf(), comparePair = null)

internal fun Loaded.withToggledSelection(favoriteId: Long): Loaded {
  val next = when {
    favoriteId in selectedFavoriteIds -> (selectedFavoriteIds - favoriteId).toPersistentSet()
    selectedFavoriteIds.size >= SelectionLimit -> selectedFavoriteIds
    else -> (selectedFavoriteIds + favoriteId).toPersistentSet()
  }
  return copy(selectedFavoriteIds = next)
}

internal val Loaded.hasEnoughCardsForCompare: Boolean
  get() = cards.size >= SelectionLimit

internal fun LocationsUiState.canAddMoreFavorites(): Boolean =
  this !is Loaded || cards.size < MAX_FAVORITES
