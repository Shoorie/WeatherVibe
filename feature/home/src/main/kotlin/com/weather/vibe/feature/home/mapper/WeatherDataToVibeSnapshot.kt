package com.weather.vibe.feature.home.mapper

import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.domain.weather.model.WeatherCondition
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
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory

@Factory
internal class WeatherDataToVibeSnapshot {

  fun map(weather: WeatherData): WeatherSnapshot =
    WeatherSnapshot(
      temperatureC = weather.currentTemperature,
      feelsLikeC = weather.apparentTemperature,
      condition = mapCondition(weather.condition, weather.isDay),
      humidityPercent = weather.humidity,
      windKph = weather.windSpeed,
      pressureHpa = weather.surfacePressure.toInt(),
      airQualityIndex = null,
      pollenLevel = null
    )

  private fun mapCondition(condition: WeatherCondition, isDay: Boolean): Condition =
    when (condition) {
      CLEAR_SKY,
      MAINLY_CLEAR -> if (isDay) Condition.SUNNY else Condition.NIGHT
      PARTLY_CLOUDY -> Condition.PARTLY_CLOUDY
      OVERCAST,
      FOG -> Condition.CLOUDY
      DRIZZLE,
      FREEZING_DRIZZLE,
      RAIN,
      FREEZING_RAIN,
      RAIN_SHOWERS,
      THUNDERSTORM -> Condition.RAIN
      SNOW,
      SNOW_SHOWERS -> Condition.SNOW
      UNKNOWN -> Condition.PARTLY_CLOUDY
    }
}
