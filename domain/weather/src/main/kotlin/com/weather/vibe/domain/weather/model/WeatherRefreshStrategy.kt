package com.weather.vibe.domain.weather.model

sealed interface WeatherRefreshStrategy {
  data object RegenerateSuggestion : WeatherRefreshStrategy
  data object InvalidateAndRegenerate : WeatherRefreshStrategy
  data object ReformatOnly : WeatherRefreshStrategy
}
