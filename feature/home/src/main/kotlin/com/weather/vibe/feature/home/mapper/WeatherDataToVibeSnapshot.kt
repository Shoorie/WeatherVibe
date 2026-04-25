package com.weather.vibe.feature.home.mapper

import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherData

internal fun WeatherData.toVibeWeatherSnapshot(): WeatherSnapshot =
  WeatherSnapshot(
    temperatureC = currentTemperature,
    feelsLikeC = apparentTemperature,
    condition = condition.toVibeCondition(isDay = isDay),
    humidityPercent = humidity,
    windKph = windSpeed,
    pressureHpa = surfacePressure.toInt(),
    airQualityIndex = null,
    pollenLevel = null
  )

private fun WeatherCondition.toVibeCondition(isDay: Boolean): Condition =
  when (this) {
    WeatherCondition.CLEAR_SKY,
    WeatherCondition.MAINLY_CLEAR ->
      if (isDay) Condition.SUNNY else Condition.NIGHT
    WeatherCondition.PARTLY_CLOUDY -> Condition.PARTLY_CLOUDY
    WeatherCondition.OVERCAST,
    WeatherCondition.FOG -> Condition.CLOUDY
    WeatherCondition.DRIZZLE,
    WeatherCondition.FREEZING_DRIZZLE,
    WeatherCondition.RAIN,
    WeatherCondition.FREEZING_RAIN,
    WeatherCondition.RAIN_SHOWERS,
    WeatherCondition.THUNDERSTORM -> Condition.RAIN
    WeatherCondition.SNOW,
    WeatherCondition.SNOW_SHOWERS -> Condition.SNOW
    WeatherCondition.UNKNOWN -> Condition.PARTLY_CLOUDY
  }
