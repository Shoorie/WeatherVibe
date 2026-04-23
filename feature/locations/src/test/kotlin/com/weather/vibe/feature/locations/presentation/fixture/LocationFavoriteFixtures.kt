package com.weather.vibe.feature.locations.presentation.fixture

import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.testing.location.fixture.LocationFixtures.KRAKOW
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import java.time.Instant

internal object LocationFavoriteFixtures {

  const val WARSAW_FAVORITE_ID = 10L
  const val KRAKOW_FAVORITE_ID = 11L
  const val DEFAULT_LABEL = "Dom"
  const val TEMPERATURE_C = 22.0
  const val FEELS_LIKE_C = 20.0
  const val HIGH_C = 26.0
  const val LOW_C = 16.0
  const val HUMIDITY = 55
  const val WIND_KPH = 12.0
  const val PRECIPITATION = 20
  val UPDATED_AT: Instant = Instant.parse("2026-04-23T10:00:00Z")
  val HOURLY: List<Double> = (0 until 24).map { it.toDouble() }

  val WARSAW_FAVORITE: LocationFavorite = favorite(
    id = WARSAW_FAVORITE_ID,
    location = WARSAW,
    isDefault = true,
    label = DEFAULT_LABEL
  )

  val KRAKOW_FAVORITE: LocationFavorite = favorite(
    id = KRAKOW_FAVORITE_ID,
    location = KRAKOW,
    isDefault = false,
    label = null,
    position = 1
  )

  val WARSAW_SNAPSHOT: LocationWeatherSnapshot = snapshot(locationId = WARSAW.id)
  val KRAKOW_SNAPSHOT: LocationWeatherSnapshot = snapshot(locationId = KRAKOW.id)

  val WARSAW_WITH_WEATHER: LocationFavoriteWithWeather = LocationFavoriteWithWeather(
    favorite = WARSAW_FAVORITE,
    snapshot = WARSAW_SNAPSHOT
  )

  val KRAKOW_WITH_WEATHER: LocationFavoriteWithWeather = LocationFavoriteWithWeather(
    favorite = KRAKOW_FAVORITE,
    snapshot = KRAKOW_SNAPSHOT
  )

  fun favorite(
    id: Long = WARSAW_FAVORITE_ID,
    location: Location = WARSAW,
    isDefault: Boolean = true,
    label: String? = DEFAULT_LABEL,
    position: Int = 0
  ): LocationFavorite = LocationFavorite(
    id = id,
    isDefault = isDefault,
    label = label,
    location = location,
    position = position
  )

  fun snapshot(
    locationId: Long = WARSAW.id,
    condition: SimplifiedCondition = SUNNY,
    feelsLikeC: Double = FEELS_LIKE_C,
    highC: Double = HIGH_C,
    hourly: List<Double> = HOURLY,
    humidity: Int = HUMIDITY,
    isDay: Boolean = true,
    lowC: Double = LOW_C,
    precipitationChance: Int = PRECIPITATION,
    temperatureC: Double = TEMPERATURE_C,
    updatedAt: Instant = UPDATED_AT,
    windKph: Double = WIND_KPH
  ): LocationWeatherSnapshot = LocationWeatherSnapshot(
    condition = condition,
    feelsLikeC = feelsLikeC,
    highC = highC,
    hourlyTemperaturesC = hourly,
    humidityPercent = humidity,
    isDay = isDay,
    locationId = locationId,
    lowC = lowC,
    precipitationChancePercent = precipitationChance,
    temperatureC = temperatureC,
    updatedAt = updatedAt,
    windKph = windKph
  )
}
