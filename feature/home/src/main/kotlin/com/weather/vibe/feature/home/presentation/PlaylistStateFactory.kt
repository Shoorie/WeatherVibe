package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import org.koin.core.annotation.Factory

@Factory
internal class PlaylistStateFactory {

  fun create(suggestion: WeatherSuggestion): PlaylistUiState.Loaded {

    val genreNames = suggestion.genres.map { it.trim() }
    val spotifyQuery = genreNames.joinToString(separator = " ")
    val ytQuery = genreNames.firstOrNull().orEmpty()

    return PlaylistUiState.Loaded(
      genres = genreNames.map { GenreChipUiState(name = it) },
      mood = suggestion.mood,
      moodDescription = suggestion.moodDescription,
      spotifyQuery = "$SPOTIFY_SCHEME$spotifyQuery",
      ytMusicUrl = "$YT_MUSIC_BASE_URL$ytQuery"
    )
  }

  private companion object {
    const val SPOTIFY_SCHEME = "spotify:search:"
    const val YT_MUSIC_BASE_URL = "https://music.youtube.com/search?q="
  }
}
