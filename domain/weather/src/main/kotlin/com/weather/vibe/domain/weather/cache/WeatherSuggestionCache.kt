package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

interface WeatherSuggestionCache {

  suspend fun delete(
    dispositionEntries: List<UserDispositionEntry>,
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  )

  suspend fun get(
    dispositionEntries: List<UserDispositionEntry>,
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  ): CachedWeatherSuggestion?

  suspend fun save(
    dispositionEntries: List<UserDispositionEntry>,
    languageTag: String,
    suggestion: WeatherSuggestion,
    tone: BriefTone,
    weatherKey: WeatherKey
  )
}
