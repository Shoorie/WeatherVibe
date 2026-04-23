package com.weather.vibe.feature.locations.presentation.state

internal enum class LocationWeatherUi(val emoji: String) {
  Sunny(emoji = "☀️"),
  PartlyCloudy(emoji = "⛅"),
  Cloudy(emoji = "☁️"),
  Rain(emoji = "🌧️"),
  Snow(emoji = "❄️"),
  Night(emoji = "🌙")
}
