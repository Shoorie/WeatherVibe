package com.weather.vibe.data.weather.mapper

import com.weather.vibe.data.weather.local.cache.CachedDailyWeather
import com.weather.vibe.data.weather.local.cache.CachedHourlyWeather
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition
import org.koin.core.annotation.Factory

@Factory
internal class CachedWeatherMapper {

  fun toCached(hourly: HourlyWeather): CachedHourlyWeather =
    CachedHourlyWeather(
      apparentTemperature = hourly.apparentTemperature,
      cloudCover = hourly.cloudCover,
      condition = hourly.condition.name,
      dewPoint = hourly.dewPoint,
      humidity = hourly.humidity,
      precipitation = hourly.precipitation,
      precipitationProbability = hourly.precipitationProbability,
      surfacePressure = hourly.surfacePressure,
      temperature = hourly.temperature,
      time = hourly.time,
      visibility = hourly.visibility,
      windGusts = hourly.windGusts,
      windSpeed = hourly.windSpeed
    )

  fun toCached(daily: DailyWeather): CachedDailyWeather =
    CachedDailyWeather(
      condition = daily.condition.name,
      date = daily.date,
      maxTemperature = daily.maxTemperature,
      minTemperature = daily.minTemperature,
      precipitationProbability = daily.precipitationProbability,
      precipitationSum = daily.precipitationSum,
      sunrise = daily.sunrise,
      sunset = daily.sunset,
      uvIndexMax = daily.uvIndexMax,
      windGustsMax = daily.windGustsMax,
      windSpeedMax = daily.windSpeedMax
    )

  fun toDomain(cached: CachedHourlyWeather): HourlyWeather =
    HourlyWeather(
      apparentTemperature = cached.apparentTemperature,
      cloudCover = cached.cloudCover,
      condition = cached.condition.toWeatherCondition(),
      dewPoint = cached.dewPoint,
      humidity = cached.humidity,
      precipitation = cached.precipitation,
      precipitationProbability = cached.precipitationProbability,
      surfacePressure = cached.surfacePressure,
      temperature = cached.temperature,
      time = cached.time,
      visibility = cached.visibility,
      windGusts = cached.windGusts,
      windSpeed = cached.windSpeed
    )

  fun toDomain(cached: CachedDailyWeather): DailyWeather =
    DailyWeather(
      condition = cached.condition.toWeatherCondition(),
      date = cached.date,
      maxTemperature = cached.maxTemperature,
      minTemperature = cached.minTemperature,
      precipitationProbability = cached.precipitationProbability,
      precipitationSum = cached.precipitationSum,
      sunrise = cached.sunrise,
      sunset = cached.sunset,
      uvIndexMax = cached.uvIndexMax,
      windGustsMax = cached.windGustsMax,
      windSpeedMax = cached.windSpeedMax
    )

  private fun String.toWeatherCondition(): WeatherCondition =
    runCatching { WeatherCondition.valueOf(this) }
      .getOrDefault(WeatherCondition.UNKNOWN)
}
