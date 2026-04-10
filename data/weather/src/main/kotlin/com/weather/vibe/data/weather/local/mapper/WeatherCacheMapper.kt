package com.weather.vibe.data.weather.local.mapper

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.weather.local.cache.CachedDailyWeather
import com.weather.vibe.data.weather.local.cache.CachedHourlyWeather
import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherData
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory

@Factory
internal class WeatherCacheMapper(
  private val cachedWeatherMapper: CachedWeatherMapper,
  private val timeProvider: TimeProvider
) {

  private val json = Json { ignoreUnknownKeys = true }

  fun toEntity(weather: WeatherData): WeatherCacheEntity =
    WeatherCacheEntity(
      locationId = weather.coordinates.id,
      cityName = weather.coordinates.name,
      currentTemperature = weather.currentTemperature,
      currentConditionName = weather.condition.name,
      windSpeed = weather.windSpeed,
      windDirection = weather.windDirection,
      humidity = weather.humidity,
      isDay = weather.isDay,
      hourlyForecastJson = encodeHourly(weather.hourlyForecast),
      dailyForecastJson = encodeDaily(weather.dailyForecast),
      lastUpdated = timeProvider.nowEpochMillis()
    )

  fun toDomain(entity: WeatherCacheEntity): WeatherData {

    val hourlyForecast = decodeHourly(entity.hourlyForecastJson)
    val dailyForecast = decodeDaily(entity.dailyForecastJson)
    val firstHour = hourlyForecast.firstOrNull()
    val (latitude, longitude) = Coordinates.parseId(entity.locationId)

    return WeatherData(
      apparentTemperature = firstHour?.apparentTemperature ?: entity.currentTemperature,
      cloudCover = firstHour?.cloudCover ?: 0,
      condition = parseCondition(entity.currentConditionName),
      coordinates = Coordinates(
        name = entity.cityName,
        latitude = latitude,
        longitude = longitude
      ),
      currentTemperature = entity.currentTemperature,
      dailyForecast = dailyForecast,
      dewPoint = firstHour?.dewPoint ?: 0.0,
      hourlyForecast = hourlyForecast,
      humidity = entity.humidity,
      isDay = entity.isDay,
      precipitation = firstHour?.precipitation ?: 0.0,
      surfacePressure = firstHour?.surfacePressure ?: 0.0,
      visibility = firstHour?.visibility ?: 0.0,
      windDirection = entity.windDirection,
      windGusts = firstHour?.windGusts ?: 0.0,
      windSpeed = entity.windSpeed
    )
  }

  private fun encodeHourly(forecast: List<HourlyWeather>): String =
    json.encodeToString(forecast.map(cachedWeatherMapper::toCached))

  private fun encodeDaily(forecast: List<DailyWeather>): String =
    json.encodeToString(forecast.map(cachedWeatherMapper::toCached))

  private fun decodeHourly(payload: String): List<HourlyWeather> =
    runCatching {
      json.decodeFromString<List<CachedHourlyWeather>>(payload)
        .map(cachedWeatherMapper::toDomain)
    }.getOrDefault(emptyList())

  private fun decodeDaily(payload: String): List<DailyWeather> =
    runCatching {
      json.decodeFromString<List<CachedDailyWeather>>(payload)
        .map(cachedWeatherMapper::toDomain)
    }.getOrDefault(emptyList())

  private fun parseCondition(name: String): WeatherCondition =
    runCatching { WeatherCondition.valueOf(name) }
      .getOrDefault(WeatherCondition.UNKNOWN)
}
