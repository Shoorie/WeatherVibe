package com.weather.vibe.data.weather.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.weather.cache.WeatherAiCache
import com.weather.vibe.domain.weather.model.MoodPlaylist
import com.weather.vibe.domain.weather.model.WeatherAiContent
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import java.time.LocalDate

@Single(binds = [WeatherAiCache::class])
internal class DefaultWeatherAiCache(
  @param:WeatherAiDataStoreQualifier
  private val dataStore: DataStore<WeatherAiCacheData>
) : WeatherAiCache {

  override suspend fun get(cityName: String, date: LocalDate): WeatherAiContent? {

    val data = dataStore.data.first()
    val isValid = data.cityName == cityName &&
      data.date == date.toString() &&
      data.briefingText.isNotBlank() &&
      data.mood.isNotBlank() &&
      data.genresCsv.isNotBlank()

    if (!isValid) return null

    return WeatherAiContent(
      briefing = data.briefingText,
      playlist = MoodPlaylist(
        genres = data.genresCsv.split(",").map { it.trim() }.filter { it.isNotBlank() },
        mood = data.mood
      )
    )
  }

  override suspend fun save(cityName: String, content: WeatherAiContent, date: LocalDate) {
    dataStore.updateData {
      it.toBuilder()
        .setCityName(cityName)
        .setDate(date.toString())
        .setBriefingText(content.briefing)
        .setMood(content.playlist.mood)
        .setGenresCsv(content.playlist.genres.joinToString(separator = ","))
        .build()
    }
  }
}
