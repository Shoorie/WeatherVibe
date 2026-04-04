package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

interface WeatherSuggestionCache {

  suspend fun delete(
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  )

  suspend fun get(
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey
  ): CachedWeatherSuggestion?

  suspend fun save(
    languageTag: String,
    suggestion: WeatherSuggestion,
    tone: BriefTone,
    weatherKey: WeatherKey
  )
}
