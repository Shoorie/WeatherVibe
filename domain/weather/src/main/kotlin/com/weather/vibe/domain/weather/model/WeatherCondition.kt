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
      CODE_CLEAR_SKY -> CLEAR_SKY
      CODE_MAINLY_CLEAR -> MAINLY_CLEAR
      CODE_PARTLY_CLOUDY -> PARTLY_CLOUDY
      CODE_OVERCAST -> OVERCAST
      CODE_FOG, CODE_FOG_DEPOSITING_RIME -> FOG
      CODE_DRIZZLE_LIGHT, CODE_DRIZZLE_MODERATE, CODE_DRIZZLE_DENSE -> DRIZZLE
      CODE_FREEZING_DRIZZLE_LIGHT, CODE_FREEZING_DRIZZLE_DENSE -> FREEZING_DRIZZLE
      CODE_RAIN_SLIGHT, CODE_RAIN_MODERATE, CODE_RAIN_HEAVY -> RAIN
      CODE_FREEZING_RAIN_LIGHT, CODE_FREEZING_RAIN_HEAVY -> FREEZING_RAIN
      CODE_SNOW_SLIGHT, CODE_SNOW_MODERATE, CODE_SNOW_HEAVY, CODE_SNOW_GRAINS -> SNOW
      CODE_RAIN_SHOWERS_SLIGHT,
      CODE_RAIN_SHOWERS_MODERATE,
      CODE_RAIN_SHOWERS_VIOLENT -> RAIN_SHOWERS
      CODE_SNOW_SHOWERS_SLIGHT, CODE_SNOW_SHOWERS_HEAVY -> SNOW_SHOWERS
      CODE_THUNDERSTORM, CODE_THUNDERSTORM_HAIL_SLIGHT, CODE_THUNDERSTORM_HAIL_HEAVY -> THUNDERSTORM
      else -> UNKNOWN
    }

    private const val CODE_CLEAR_SKY = 0
    private const val CODE_MAINLY_CLEAR = 1
    private const val CODE_PARTLY_CLOUDY = 2
    private const val CODE_OVERCAST = 3
    private const val CODE_FOG = 45
    private const val CODE_FOG_DEPOSITING_RIME = 48
    private const val CODE_DRIZZLE_LIGHT = 51
    private const val CODE_DRIZZLE_MODERATE = 53
    private const val CODE_DRIZZLE_DENSE = 55
    private const val CODE_FREEZING_DRIZZLE_LIGHT = 56
    private const val CODE_FREEZING_DRIZZLE_DENSE = 57
    private const val CODE_RAIN_SLIGHT = 61
    private const val CODE_RAIN_MODERATE = 63
    private const val CODE_RAIN_HEAVY = 65
    private const val CODE_FREEZING_RAIN_LIGHT = 66
    private const val CODE_FREEZING_RAIN_HEAVY = 67
    private const val CODE_SNOW_SLIGHT = 71
    private const val CODE_SNOW_MODERATE = 73
    private const val CODE_SNOW_HEAVY = 75
    private const val CODE_SNOW_GRAINS = 77
    private const val CODE_RAIN_SHOWERS_SLIGHT = 80
    private const val CODE_RAIN_SHOWERS_MODERATE = 81
    private const val CODE_RAIN_SHOWERS_VIOLENT = 82
    private const val CODE_SNOW_SHOWERS_SLIGHT = 85
    private const val CODE_SNOW_SHOWERS_HEAVY = 86
    private const val CODE_THUNDERSTORM = 95
    private const val CODE_THUNDERSTORM_HAIL_SLIGHT = 96
    private const val CODE_THUNDERSTORM_HAIL_HEAVY = 99
  }
}
