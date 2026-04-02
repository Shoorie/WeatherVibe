package com.weather.vibe.domain.weather.model

import com.weather.vibe.domain.settings.model.BriefTone

data class CachedWeatherSuggestion(
  val fetchedAt: Long,
  val suggestion: WeatherSuggestion,
  val tone: BriefTone,
  val weatherKey: WeatherKey
) {

  fun isValid(excludedGenres: Set<String>): Boolean =
    !isExpired() && suggestion.genres.none { it in excludedGenres }

  private fun isExpired(): Boolean =
    System.currentTimeMillis() - fetchedAt > TTL_MILLIS

  private companion object {
    const val TTL_MILLIS = 24 * 60 * 60 * 1000L
  }
}
