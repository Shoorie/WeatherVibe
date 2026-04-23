package com.weather.vibe.feature.search.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed interface SearchUiState {

  val query: String

  @Immutable
  data class Idle(
    override val query: String = ""
  ) : SearchUiState

  @Immutable
  data class Searching(
    override val query: String
  ) : SearchUiState

  @Immutable
  data class Recents(
    override val query: String,
    val locations: ImmutableList<LocationItemUiState>
  ) : SearchUiState

  @Immutable
  data class Results(
    override val query: String,
    val locations: ImmutableList<LocationItemUiState>
  ) : SearchUiState

  @Immutable
  data class Empty(
    override val query: String
  ) : SearchUiState

  @Immutable
  data class Error(
    override val query: String,
    val message: String
  ) : SearchUiState
}

internal fun SearchUiState.withQuery(query: String): SearchUiState =
  when (this) {
    is Idle -> copy(query = query)
    is Searching -> copy(query = query)
    is Recents -> copy(query = query)
    is Results -> copy(query = query)
    is Empty -> copy(query = query)
    is Error -> copy(query = query)
  }
