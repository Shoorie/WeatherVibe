package com.weather.vibe.data.weather.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.weather.cache.BriefingCache
import com.weather.vibe.domain.weather.cache.MoodPlaylistCache
import com.weather.vibe.domain.weather.model.MoodPlaylist
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import java.time.LocalDate

@Single(binds = [BriefingCache::class, MoodPlaylistCache::class])
internal class DefaultWeatherAiCache(
  @param:BriefingDataStoreQualifier
  private val dataStore: DataStore<BriefingCacheData>
) : BriefingCache, MoodPlaylistCache {

  override suspend fun get(cityName: String, date: LocalDate): String? {
    val data = dataStore.data.first()
    return data.briefingText.takeIf {
      data.cityName == cityName &&
        data.date == date.toString() &&
        it.isNotBlank()
    }
  }

  override suspend fun save(cityName: String, date: LocalDate, text: String) {
    dataStore.updateData {
      it.toBuilder()
        .setCityName(cityName)
        .setDate(date.toString())
        .setBriefingText(text)
        .build()
    }
  }

  override suspend fun getPlaylist(cityName: String, date: LocalDate): MoodPlaylist? {
    val data = dataStore.data.first()
    val isValid = data.cityName == cityName &&
      data.date == date.toString() &&
      data.mood.isNotBlank() &&
      data.genresCsv.isNotBlank()
    if (!isValid) return null
    return MoodPlaylist(
      genres = data.genresCsv.split(",").map { it.trim() }.filter { it.isNotBlank() },
      mood = data.mood
    )
  }

  override suspend fun save(cityName: String, date: LocalDate, playlist: MoodPlaylist) {
    dataStore.updateData {
      it.toBuilder()
        .setCityName(cityName)
        .setDate(date.toString())
        .setMood(playlist.mood)
        .setGenresCsv(playlist.genres.joinToString(separator = ","))
        .build()
    }
  }
}
