package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.MoodPlaylist
import org.koin.core.annotation.Factory

@Factory
internal class ParsePlaylistResponse {

  operator fun invoke(response: String): MoodPlaylist {

    val lines = response.trim().lines()

    val mood = lines.firstOrNull { it.startsWith(MOOD_PREFIX) }
      ?.removePrefix(MOOD_PREFIX)
      ?.trim()
      .orEmpty()

    val genres = lines.firstOrNull { it.startsWith(GENRES_PREFIX) }
      ?.removePrefix(GENRES_PREFIX)
      ?.split(",")
      ?.map { it.trim() }
      ?.filter { it.isNotBlank() }
      .orEmpty()

    return MoodPlaylist(genres = genres, mood = mood)
  }

  private companion object {
    const val GENRES_PREFIX = "GENRES:"
    const val MOOD_PREFIX = "MOOD:"
  }
}
