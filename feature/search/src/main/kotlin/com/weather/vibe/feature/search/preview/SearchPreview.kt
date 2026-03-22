package com.weather.vibe.feature.search.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import com.weather.vibe.feature.search.preview.params.SearchPreviewParams

internal class SearchPreview :
  PreviewParameterProvider<SearchPreviewParams> {

  private val idle: SearchPreviewParams =
    SearchPreviewParams(
      query = "",
      state = Idle
    )

  private val recents: SearchPreviewParams =
    SearchPreviewParams(
      query = "",
      state = Recents(
        locations = listOf(
          LocationItemUiState(
            admin1 = "Mazowieckie",
            country = "Polska",
            id = 1L,
            latitude = 52.229,
            longitude = 21.011,
            name = "Warszawa",
            subtitle = "Mazowieckie, Polska",
            temperature = "15°"
          ),
          LocationItemUiState(
            admin1 = "Dolnośląskie",
            country = "Polska",
            id = 2L,
            latitude = 51.107,
            longitude = 17.038,
            name = "Wrocław",
            subtitle = "Dolnośląskie, Polska",
            temperature = "13°"
          ),
          LocationItemUiState(
            admin1 = "Kujawsko-Pomorskie",
            country = "Polska",
            id = 3L,
            latitude = 53.013,
            longitude = 18.598,
            name = "Toruń",
            subtitle = "Kujawsko-Pomorskie, Polska",
            temperature = "11°"
          )
        )
      )
    )

  private val searching: SearchPreviewParams =
    SearchPreviewParams(
      query = "Krak",
      state = Searching
    )

  private val results: SearchPreviewParams =
    SearchPreviewParams(
      query = "Krak",
      state = Results(
        locations = listOf(
          LocationItemUiState(
            admin1 = "Małopolskie",
            country = "Polska",
            id = 4L,
            latitude = 50.064,
            longitude = 19.944,
            name = "Kraków",
            subtitle = "Małopolskie, Polska"
          ),
          LocationItemUiState(
            admin1 = null,
            country = "Czechy",
            id = 5L,
            latitude = 50.208,
            longitude = 15.833,
            name = "Krakowiec",
            subtitle = "Czechy"
          )
        )
      )
    )

  private val empty: SearchPreviewParams =
    SearchPreviewParams(
      query = "xyzabc",
      state = Empty(query = "xyzabc")
    )

  override val values: Sequence<SearchPreviewParams> =
    sequenceOf(idle, recents, searching, results, empty)
}
