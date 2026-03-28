package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.weather.model.MoodPlaylist
import java.time.LocalDate

interface MoodPlaylistCache {
  suspend fun get(cityName: String, date: LocalDate): MoodPlaylist?
  suspend fun save(cityName: String, date: LocalDate, playlist: MoodPlaylist)
}
