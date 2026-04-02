package com.weather.vibe.domain.weather.model

data class WeatherSuggestion(
  val briefText: String,
  val genres: List<String>,
  val mood: String,
  val moodDescription: String
)
