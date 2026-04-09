package com.weather.vibe.data.weather.mapper

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.weather.local.cache.CachedDailyWeather
import com.weather.vibe.data.weather.local.cache.CachedHourlyWeather
import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity
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

  fun toEntity(data: WeatherData): WeatherCacheEntity =
    WeatherCacheEntity(
      locationId = "${data.latitude}$LOCATION_ID_SEPARATOR${data.longitude}",
      cityName = data.cityName,
      currentTemperature = data.currentTemperature,
      currentConditionName = data.condition.name,
      windSpeed = data.windSpeed,
      windDirection = data.windDirection,
      humidity = data.humidity,
      isDay = data.isDay,
      hourlyForecastJson = json.encodeToString(data.hourlyForecast.map(cachedWeatherMapper::toCached)),
      dailyForecastJson = json.encodeToString(data.dailyForecast.map(cachedWeatherMapper::toCached)),
      lastUpdated = timeProvider.nowEpochMillis()
    )

  fun toDomain(entity: WeatherCacheEntity): WeatherData {

    val hourlyList = runCatching {
      json.decodeFromString<List<CachedHourlyWeather>>(entity.hourlyForecastJson)
        .map(cachedWeatherMapper::toDomain)
    }.getOrDefault(emptyList())

    val dailyList = runCatching {
      json.decodeFromString<List<CachedDailyWeather>>(entity.dailyForecastJson)
        .map(cachedWeatherMapper::toDomain)
    }.getOrDefault(emptyList())

    val firstHourly = hourlyList.firstOrNull()
    val (latitude, longitude) = parseLocation(entity.locationId)

    return WeatherData(
      apparentTemperature = firstHourly?.apparentTemperature ?: entity.currentTemperature,
      cityName = entity.cityName,
      cloudCover = firstHourly?.cloudCover ?: 0,
      condition = parseCondition(entity.currentConditionName),
      currentTemperature = entity.currentTemperature,
      dailyForecast = dailyList,
      dewPoint = firstHourly?.dewPoint ?: 0.0,
      hourlyForecast = hourlyList,
      humidity = entity.humidity,
      isDay = entity.isDay,
      latitude = latitude,
      longitude = longitude,
      precipitation = firstHourly?.precipitation ?: 0.0,
      surfacePressure = firstHourly?.surfacePressure ?: 0.0,
      visibility = firstHourly?.visibility ?: 0.0,
      windDirection = entity.windDirection,
      windGusts = firstHourly?.windGusts ?: 0.0,
      windSpeed = entity.windSpeed
    )
  }

  private fun parseLocation(locationId: String): Pair<Double, Double> {
    val parts = locationId.split(LOCATION_ID_SEPARATOR)
    val latitude = parts.getOrNull(0)?.toDoubleOrNull() ?: 0.0
    val longitude = parts.getOrNull(1)?.toDoubleOrNull() ?: 0.0
    return latitude to longitude
  }

  private fun parseCondition(name: String): WeatherCondition =
    runCatching { WeatherCondition.valueOf(name) }
      .getOrDefault(WeatherCondition.UNKNOWN)

  private companion object {
    const val LOCATION_ID_SEPARATOR = ","
  }
}
