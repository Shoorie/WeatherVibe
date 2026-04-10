package com.weather.vibe.feature.search.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching

internal class SearchPreview : PreviewParameterProvider<SearchUiState> {

  private val idle: SearchUiState = Idle()

  private val recents: SearchUiState = Recents(
    query = "",
    locations = listOf(
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
  )

  private val searching: SearchUiState = Searching(query = "Krak")

  private val results: SearchUiState = Results(
    query = "Krak",
    locations = listOf(
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
  )

  private val empty: SearchUiState = Empty(query = "xyzabc")

  private val error: SearchUiState = Error(
    query = "Krak",
    message = "Something went wrong"
  )

  override val values: Sequence<SearchUiState> =
    sequenceOf(idle, recents, searching, results, empty, error)
}
