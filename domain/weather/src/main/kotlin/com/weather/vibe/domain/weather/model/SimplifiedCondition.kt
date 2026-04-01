package com.weather.vibe.domain.weather.model

import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.FOG
import com.weather.vibe.domain.weather.model.WeatherCondition.FREEZING_DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.FREEZING_RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.MAINLY_CLEAR
import com.weather.vibe.domain.weather.model.WeatherCondition.OVERCAST
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN_SHOWERS
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW_SHOWERS
import com.weather.vibe.domain.weather.model.WeatherCondition.THUNDERSTORM
import com.weather.vibe.domain.weather.model.WeatherCondition.UNKNOWN

enum class SimplifiedCondition(val label: String) {

  CLOUDY("cloudy"),
  FOGGY("foggy"),
  RAINY("rainy"),
  SNOWY("snowy"),
  STORMY("stormy"),
  SUNNY("sunny");

  companion object {

    fun from(condition: WeatherCondition): SimplifiedCondition =
      when (condition) {
        CLEAR_SKY,
        MAINLY_CLEAR -> SUNNY

        PARTLY_CLOUDY,
        OVERCAST -> CLOUDY

        DRIZZLE,
        FREEZING_DRIZZLE,
        FREEZING_RAIN,
        RAIN,
        RAIN_SHOWERS -> RAINY

        THUNDERSTORM -> STORMY
        SNOW,
        SNOW_SHOWERS -> SNOWY

        FOG -> FOGGY
        UNKNOWN -> CLOUDY
      }
  }
}
