package com.weather.vibe.domain.weather.model

sealed interface WeatherBriefResult {

  data class Ready(val suggestion: WeatherSuggestion) : WeatherBriefResult

  data object LimitReached : WeatherBriefResult
}
