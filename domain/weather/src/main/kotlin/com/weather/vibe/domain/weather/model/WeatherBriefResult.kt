package com.weather.vibe.domain.weather.model

sealed interface WeatherBriefResult {

  data class Ready(val suggestion: WeatherSuggestion) : WeatherBriefResult

  data object LimitReached : WeatherBriefResult
}

fun WeatherBriefResult.briefTextOrNull(): String? = when (this) {
  is WeatherBriefResult.Ready -> suggestion.briefText
  WeatherBriefResult.LimitReached -> null
}
