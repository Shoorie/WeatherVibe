package com.weather.vibe.feature.search.preview

import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object SearchPreviewLocations {

  val sampleLocations: ImmutableList<LocationItemUiState> = persistentListOf(
    LocationItemUiState(
      id = 1L,
      name = "Warszawa",
      subtitle = "Mazowieckie, Polska",
      isFavorite = true,
      canToggleFavorite = true
    ),
    LocationItemUiState(
      id = 2L,
      name = "Wrocław",
      subtitle = "Dolnośląskie, Polska",
      isFavorite = false,
      canToggleFavorite = true
    ),
    LocationItemUiState(
      id = 3L,
      name = "Toruń",
      subtitle = "Kujawsko-Pomorskie, Polska",
      isFavorite = false,
      canToggleFavorite = false
    )
  )

  val searchResults: ImmutableList<LocationItemUiState> = persistentListOf(
    LocationItemUiState(
      id = 4L,
      name = "Kraków",
      subtitle = "Małopolskie, Polska",
      isFavorite = false,
      canToggleFavorite = true
    ),
    LocationItemUiState(
      id = 5L,
      name = "Krakowiec",
      subtitle = "Czechy",
      isFavorite = true,
      canToggleFavorite = true
    )
  )
}
