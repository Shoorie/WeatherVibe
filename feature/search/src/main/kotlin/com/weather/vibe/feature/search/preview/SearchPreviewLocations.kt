package com.weather.vibe.feature.search.preview

import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object SearchPreviewLocations {

  val sampleLocations: ImmutableList<LocationItemUiState> = persistentListOf(
    LocationItemUiState(
      id = 1L,
      name = "London",
      subtitle = "England, United Kingdom",
      isFavorite = true,
      canToggleFavorite = true
    ),
    LocationItemUiState(
      id = 2L,
      name = "Paris",
      subtitle = "Île-de-France, France",
      isFavorite = false,
      canToggleFavorite = true
    ),
    LocationItemUiState(
      id = 3L,
      name = "Tokyo",
      subtitle = "Kanto, Japan",
      isFavorite = false,
      canToggleFavorite = false
    )
  )

  val searchResults: ImmutableList<LocationItemUiState> = persistentListOf(
    LocationItemUiState(
      id = 4L,
      name = "Madrid",
      subtitle = "Community of Madrid, Spain",
      isFavorite = false,
      canToggleFavorite = true
    ),
    LocationItemUiState(
      id = 5L,
      name = "Madison",
      subtitle = "Wisconsin, United States",
      isFavorite = true,
      canToggleFavorite = true
    )
  )
}
