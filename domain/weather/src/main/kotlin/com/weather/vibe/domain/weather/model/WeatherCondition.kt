package com.weather.vibe.domain.weather.model

enum class WeatherCondition(val label: String, val emoji: String) {
  CLEAR_SKY("Clear Sky", "☀️"),
  MAINLY_CLEAR("Mainly Clear", "🌤️"),
  PARTLY_CLOUDY("Partly Cloudy", "⛅"),
  OVERCAST("Overcast", "☁️"),
  FOG("Foggy", "🌫️"),
  DRIZZLE("Drizzle", "🌦️"),
  FREEZING_DRIZZLE("Freezing Drizzle", "🌨️"),
  RAIN("Rain", "🌧️"),
  FREEZING_RAIN("Freezing Rain", "🌨️"),
  SNOW("Snow", "❄️"),
  RAIN_SHOWERS("Rain Showers", "🌧️"),
  SNOW_SHOWERS("Snow Showers", "🌨️"),
  THUNDERSTORM("Thunderstorm", "⛈️"),
  UNKNOWN("Unknown", "🌡️");

  companion object {
    fun fromWmoCode(code: Int): WeatherCondition = when (code) {
      0 -> CLEAR_SKY
      1 -> MAINLY_CLEAR
      2 -> PARTLY_CLOUDY
      3 -> OVERCAST
      45, 48 -> FOG
      51, 53, 55 -> DRIZZLE
      56, 57 -> FREEZING_DRIZZLE
      61, 63, 65 -> RAIN
      66, 67 -> FREEZING_RAIN
      71, 73, 75, 77 -> SNOW
      80, 81, 82 -> RAIN_SHOWERS
      85, 86 -> SNOW_SHOWERS
      95, 96, 99 -> THUNDERSTORM
      else -> UNKNOWN
    }
  }
}
