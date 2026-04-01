package com.weather.vibe.data.weather.local

import com.weather.vibe.data.weather.local.dao.AiSuggestionDao
import com.weather.vibe.data.weather.local.mapper.toDomain
import com.weather.vibe.data.weather.local.mapper.toEntity
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.cache.WeatherAiCache
import com.weather.vibe.domain.weather.model.AiSuggestion
import com.weather.vibe.domain.weather.model.CachedAiSuggestion
import com.weather.vibe.domain.weather.model.WeatherKey
import org.koin.core.annotation.Single

@Single(binds = [WeatherAiCache::class])
internal class DefaultWeatherAiCache(
  private val dao: AiSuggestionDao
) : WeatherAiCache {

  override suspend fun get(
    tone: BriefTone,
    weatherKey: WeatherKey
  ): CachedAiSuggestion? {
    val entity = dao.get(
      keyHash = weatherKey.toHash(),
      tone = tone.name
    ) ?: return null
    return entity.toDomain()
  }

  override suspend fun save(
    suggestion: AiSuggestion,
    tone: BriefTone,
    weatherKey: WeatherKey
  ) {
    val cached = CachedAiSuggestion(
      fetchedAt = System.currentTimeMillis(),
      suggestion = suggestion,
      tone = tone,
      weatherKey = weatherKey
    )
    dao.upsert(entity = cached.toEntity())
  }
}
