package com.weather.vibe.data.weather.local

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.weather.local.dao.WeatherSuggestionDao
import com.weather.vibe.data.weather.local.mapper.WeatherSuggestionEntityMapper
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import org.koin.core.annotation.Single

@Single(binds = [WeatherSuggestionCache::class])
internal class DefaultWeatherSuggestionCache(
  private val dao: WeatherSuggestionDao,
  private val mapper: WeatherSuggestionEntityMapper,
  private val timeProvider: TimeProvider
) : WeatherSuggestionCache {

  override suspend fun delete(
    dispositionEntries: List<UserDispositionEntry>,
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  ) {
    dao.delete(
      keyHash = mapper.toLocalizedHash(weatherKey, languageTag, dispositionEntries),
      tone = tone.name
    )
  }

  override suspend fun get(
    dispositionEntries: List<UserDispositionEntry>,
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  ): CachedWeatherSuggestion? {
    val entity = dao.get(
      keyHash = mapper.toLocalizedHash(weatherKey, languageTag, dispositionEntries),
      tone = tone.name
    ) ?: return null
    return mapper.toDomain(entity)
  }

  override suspend fun save(
    dispositionEntries: List<UserDispositionEntry>,
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
    dao.upsert(
      entity = mapper.toEntity(
        cached = cached,
        dispositionEntries = dispositionEntries,
        languageTag = languageTag
      )
    )
  }
}
