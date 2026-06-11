package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.PlaylistQuery
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import org.koin.core.annotation.Factory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Factory
class BuildPlaylistQuery {

  operator fun invoke(suggestion: WeatherSuggestion): PlaylistQuery {
    val trimmedGenres = suggestion.genres.map { it.trim() }
    return PlaylistQuery(
      spotifyApp = spotifyAppUri(trimmedGenres),
      spotifyWeb = spotifyWebUrl(trimmedGenres),
      ytMusic = ytMusicQuery(trimmedGenres)
    )
  }

  private fun spotifyAppUri(genres: List<String>): String =
    SPOTIFY_APP_SCHEME + genres.joinToString(separator = GENRE_SEPARATOR)

  private fun spotifyWebUrl(genres: List<String>): String =
    SPOTIFY_SEARCH_URL + encodePath(genres.joinToString(separator = GENRE_SEPARATOR))

  private fun ytMusicQuery(genres: List<String>): String =
    YT_MUSIC_BASE_URL + genres
      .firstOrNull().orEmpty()

  private fun encodePath(query: String): String =
    URLEncoder.encode(query, StandardCharsets.UTF_8.name())
      .replace(PLUS, ENCODED_SPACE)

  private companion object {
    const val SPOTIFY_APP_SCHEME = "spotify:search:"
    const val SPOTIFY_SEARCH_URL = "https://open.spotify.com/search/"
    const val YT_MUSIC_BASE_URL = "https://music.youtube.com/search?q="
    const val GENRE_SEPARATOR = " "
    const val PLUS = "+"
    const val ENCODED_SPACE = "%20"
  }
}
