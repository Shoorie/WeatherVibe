package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.PlaylistQuery
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import org.koin.core.annotation.Factory

@Factory
class BuildPlaylistQuery {

  operator fun invoke(suggestion: WeatherSuggestion): PlaylistQuery {
    val trimmedGenres = suggestion.genres.map { it.trim() }
    return PlaylistQuery(
      spotify = spotifyQuery(trimmedGenres),
      ytMusic = ytMusicQuery(trimmedGenres)
    )
  }

  private fun spotifyQuery(genres: List<String>): String =
    SPOTIFY_SCHEME + genres
      .joinToString(separator = GENRE_SEPARATOR)

  private fun ytMusicQuery(genres: List<String>): String =
    YT_MUSIC_BASE_URL + genres
      .firstOrNull().orEmpty()

  private companion object {
    const val SPOTIFY_SCHEME = "spotify:search:"
    const val YT_MUSIC_BASE_URL = "https://music.youtube.com/search?q="
    const val GENRE_SEPARATOR = " "
  }
}
