package com.weather.vibe.domain.location.model

data class WeatherComparison(
  val temperature: WeatherAdvantage,
  val wind: WeatherAdvantage,
  val humidity: WeatherAdvantage,
  val rain: WeatherAdvantage
)
