package com.weather.vibe.domain.location.model

data class LocationWeatherComparison(
  val temperature: LocationWeatherAdvantage,
  val wind: LocationWeatherAdvantage,
  val humidity: LocationWeatherAdvantage,
  val rain: LocationWeatherAdvantage
)
