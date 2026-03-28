package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.weather.model.WeatherAiContent
import com.weather.vibe.domain.weather.model.WeatherAiParams

interface WeatherAiCache {
  suspend fun get(params: WeatherAiParams): WeatherAiContent?
  suspend fun save(content: WeatherAiContent, params: WeatherAiParams)
}
