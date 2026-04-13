package com.weather.vibe.feature.search.preview

import com.weather.vibe.feature.search.presentation.state.LocationItemUiState

internal object SearchPreviewLocations {

  val sampleLocations: List<LocationItemUiState> = listOf(
    LocationItemUiState(
      id = 1L,
      name = "Warszawa",
      subtitle = "Mazowieckie, Polska",
      temperature = "15°"
    ),
    LocationItemUiState(
      id = 2L,
      name = "Wrocław",
      subtitle = "Dolnośląskie, Polska",
      temperature = "13°"
    ),
    LocationItemUiState(
      id = 3L,
      name = "Toruń",
      subtitle = "Kujawsko-Pomorskie, Polska",
      temperature = "11°"
    )
  )

  val searchResults: List<LocationItemUiState> = listOf(
    LocationItemUiState(
      id = 4L,
      name = "Kraków",
      subtitle = "Małopolskie, Polska",
      temperature = null
    ),
    LocationItemUiState(
      id = 5L,
      name = "Krakowiec",
      subtitle = "Czechy",
      temperature = null
    )
  )
}
