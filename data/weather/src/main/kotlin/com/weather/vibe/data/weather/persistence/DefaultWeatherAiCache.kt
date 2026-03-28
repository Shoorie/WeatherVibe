package com.weather.vibe.data.weather.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.weather.cache.WeatherAiCache
import com.weather.vibe.domain.weather.model.MoodPlaylist
import com.weather.vibe.domain.weather.model.WeatherAiContent
import com.weather.vibe.domain.weather.model.WeatherAiParams
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single(binds = [WeatherAiCache::class])
internal class DefaultWeatherAiCache(
  @param:WeatherAiQualifier
  private val dataStore: DataStore<WeatherAiCacheData>
) : WeatherAiCache {

  override suspend fun get(params: WeatherAiParams): WeatherAiContent? {

    val data = dataStore.data.first()
    val isValid = data.cityName == params.cityName &&
      data.date == params.date.toString() &&
      data.persona == params.persona.name &&
      data.excludedGenres == params.excludedGenres &&
      data.briefingText.isNotBlank() &&
      data.mood.isNotBlank() &&
      data.genresCsv.isNotBlank()

    if (!isValid) return null

    return WeatherAiContent(
      briefing = data.briefingText,
      playlist = MoodPlaylist(
        genres = data.genresCsv
          .split(GENRES_SEPARATOR)
          .map { it.trim() }
          .filter { it.isNotBlank() },
        mood = data.mood
      )
    )
  }

  override suspend fun save(content: WeatherAiContent, params: WeatherAiParams) {
    dataStore.updateData {
      it.toBuilder()
        .setCityName(params.cityName)
        .setDate(params.date.toString())
        .setBriefingText(content.briefing)
        .setMood(content.playlist.mood)
        .setGenresCsv(content.playlist.genres.joinToString(separator = GENRES_SEPARATOR))
        .setPersona(params.persona.name)
        .setExcludedGenres(params.excludedGenres)
        .build()
    }
  }

  private companion object {
    const val GENRES_SEPARATOR = ","
  }
}
