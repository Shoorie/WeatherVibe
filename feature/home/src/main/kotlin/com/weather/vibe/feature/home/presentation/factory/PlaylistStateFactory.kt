package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.usecase.BuildPlaylistQuery
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory

@Factory
internal class PlaylistStateFactory(
  private val buildPlaylistQuery: BuildPlaylistQuery
) {

  fun create(suggestion: WeatherSuggestion): PlaylistUiState.Loaded {
    val query = buildPlaylistQuery(suggestion)
    return PlaylistUiState.Loaded(
      genres = suggestion.genres.map { GenreChipUiState(name = it.trim()) }.toImmutableList(),
      mood = suggestion.mood,
      moodDescription = suggestion.moodDescription,
      spotifyQuery = query.spotify,
      ytMusicUrl = query.ytMusic
    )
  }
}
