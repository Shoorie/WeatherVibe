package com.weather.vibe.data.weather.mapper

import com.weather.vibe.data.weather.local.cache.CachedDailyWeather
import com.weather.vibe.data.weather.local.cache.CachedHourlyWeather
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition

internal fun HourlyWeather.toCached(): CachedHourlyWeather =
  CachedHourlyWeather(
    apparentTemperature = apparentTemperature,
    cloudCover = cloudCover,
    condition = condition.name,
    dewPoint = dewPoint,
    humidity = humidity,
    precipitation = precipitation,
    precipitationProbability = precipitationProbability,
    surfacePressure = surfacePressure,
    temperature = temperature,
    time = time,
    visibility = visibility,
    windGusts = windGusts,
    windSpeed = windSpeed
  )

internal fun CachedHourlyWeather.toDomain(): HourlyWeather =
  HourlyWeather(
    apparentTemperature = apparentTemperature,
    cloudCover = cloudCover,
    condition = condition.toWeatherCondition(),
    dewPoint = dewPoint,
    humidity = humidity,
    precipitation = precipitation,
    precipitationProbability = precipitationProbability,
    surfacePressure = surfacePressure,
    temperature = temperature,
    time = time,
    visibility = visibility,
    windGusts = windGusts,
    windSpeed = windSpeed
  )

internal fun DailyWeather.toCached(): CachedDailyWeather =
  CachedDailyWeather(
    condition = condition.name,
    date = date,
    maxTemperature = maxTemperature,
    minTemperature = minTemperature,
    precipitationProbability = precipitationProbability,
    precipitationSum = precipitationSum,
    sunrise = sunrise,
    sunset = sunset,
    uvIndexMax = uvIndexMax,
    windGustsMax = windGustsMax,
    windSpeedMax = windSpeedMax
  )

internal fun CachedDailyWeather.toDomain(): DailyWeather =
  DailyWeather(
    condition = condition.toWeatherCondition(),
    date = date,
    maxTemperature = maxTemperature,
    minTemperature = minTemperature,
    precipitationProbability = precipitationProbability,
    precipitationSum = precipitationSum,
    sunrise = sunrise,
    sunset = sunset,
    uvIndexMax = uvIndexMax,
    windGustsMax = windGustsMax,
    windSpeedMax = windSpeedMax
  )

private fun String.toWeatherCondition(): WeatherCondition =
  runCatching { WeatherCondition.valueOf(this) }
    .getOrDefault(WeatherCondition.UNKNOWN)
