package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.AiSuggestion
import com.weather.vibe.domain.weather.model.CachedAiSuggestion
import com.weather.vibe.domain.weather.model.WeatherKey

interface WeatherAiCache {
  suspend fun get(tone: BriefTone, weatherKey: WeatherKey): CachedAiSuggestion?
  suspend fun save(suggestion: AiSuggestion, tone: BriefTone, weatherKey: WeatherKey)
}
