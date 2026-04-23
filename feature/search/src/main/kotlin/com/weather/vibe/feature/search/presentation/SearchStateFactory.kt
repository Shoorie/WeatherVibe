package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.AddFavorite
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory

@Factory
internal class SearchStateFactory(
  private val subtitle: LocationSubtitleFormatter
) {

  fun errorState(query: String, message: String): SearchUiState =
    Error(query = query, message = message)

  fun recentsStateOrIdle(
    query: String,
    locations: List<Location>,
    favoriteLocationIds: Set<Long>
  ): SearchUiState = when (locations.isEmpty()) {
    true -> Idle(query = query)
    false -> Recents(
      query = query,
      locations = createItems(locations = locations, favoriteLocationIds = favoriteLocationIds)
    )
  }

  fun resultsStateOrEmpty(
    query: String,
    locations: List<Location>,
    favoriteLocationIds: Set<Long>
  ): SearchUiState = when (locations.isEmpty()) {
    true -> Empty(query = query)
    false -> Results(
      query = query,
      locations = createItems(locations = locations, favoriteLocationIds = favoriteLocationIds)
    )
  }

  fun refreshFavorites(
    current: SearchUiState,
    recents: List<Location>,
    lastResults: List<Location>,
    favoriteLocationIds: Set<Long>
  ): SearchUiState = when (current) {
    is Recents -> current.copy(
      locations = createItems(locations = recents, favoriteLocationIds = favoriteLocationIds)
    )
    is Results -> current.copy(
      locations = createItems(locations = lastResults, favoriteLocationIds = favoriteLocationIds)
    )
    is Idle, is Searching, is Empty, is Error -> current
  }

  private fun createItems(
    locations: List<Location>,
    favoriteLocationIds: Set<Long>
  ): ImmutableList<LocationItemUiState> {
    val hasCapacity = favoriteLocationIds.size < AddFavorite.FAVORITES_LIMIT
    return locations
      .map { location ->
        createItem(
          location = location,
          isFavorite = location.id in favoriteLocationIds,
          hasCapacity = hasCapacity
        )
      }
      .toImmutableList()
  }

  private fun createItem(
    location: Location,
    isFavorite: Boolean,
    hasCapacity: Boolean
  ): LocationItemUiState =
    LocationItemUiState(
      id = location.id,
      name = location.name,
      subtitle = subtitle(location),
      isFavorite = isFavorite,
      canToggleFavorite = isFavorite || hasCapacity
    )
}
