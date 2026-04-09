package com.weather.vibe.data.weather.local

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.weather.local.dao.WeatherSuggestionDao
import com.weather.vibe.data.weather.local.mapper.toDomain
import com.weather.vibe.data.weather.local.mapper.toEntity
import com.weather.vibe.data.weather.local.mapper.toLocalizedHash
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import org.koin.core.annotation.Single

@Single(binds = [WeatherSuggestionCache::class])
internal class DefaultWeatherSuggestionCache(
  private val dao: WeatherSuggestionDao,
  private val timeProvider: TimeProvider
) : WeatherSuggestionCache {

  override suspend fun delete(
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  ) {
    dao.delete(
      keyHash = weatherKey.toLocalizedHash(languageTag),
      tone = tone.name
    )
  }

  override suspend fun get(
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  ): CachedWeatherSuggestion? {
    val entity = dao.get(
      keyHash = weatherKey.toLocalizedHash(languageTag),
      tone = tone.name
    ) ?: return null
    return entity.toDomain()
  }

  override suspend fun save(
    languageTag: String,
    suggestion: WeatherSuggestion,
    tone: BriefTone,
    weatherKey: WeatherKey
  ) {
    val cached = CachedWeatherSuggestion(
      fetchedAt = timeProvider.nowEpochMillis(),
      suggestion = suggestion,
      tone = tone,
      weatherKey = weatherKey
    )
    dao.upsert(entity = cached.toEntity(languageTag))
  }
}
