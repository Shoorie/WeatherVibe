package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

interface WeatherSuggestionCache {
  suspend fun delete(tone: BriefTone, weatherKey: WeatherKey)
  suspend fun get(tone: BriefTone, weatherKey: WeatherKey): CachedWeatherSuggestion?
  suspend fun save(suggestion: WeatherSuggestion, tone: BriefTone, weatherKey: WeatherKey)
}
