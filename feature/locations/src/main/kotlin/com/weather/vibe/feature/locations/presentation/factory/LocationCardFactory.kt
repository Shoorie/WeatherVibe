package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class LocationCardFactory(
  private val temperatureFormatter: TemperatureFormatter,
  private val weatherFactory: LocationWeatherFactory
) {

  fun create(
    source: LocationFavoriteWithWeather,
    temperatureUnit: TemperatureUnit
  ): LocationCardUiState {
    val snapshot = source.snapshot
    return LocationCardUiState(
      favoriteId = source.favorite.id,
      feelsLike = snapshot?.feelsLikeC?.formatted(unit = temperatureUnit),
      high = snapshot?.highC?.formatted(unit = temperatureUnit),
      hourlyTemperatures = hourlyTemperatures(snapshot = snapshot),
      humidityPercent = snapshot?.humidityPercent,
      label = source.favorite.label,
      locationId = source.favorite.location.id,
      low = snapshot?.lowC?.formatted(unit = temperatureUnit),
      name = source.favorite.location.name,
      precipitationChancePercent = snapshot?.precipitationChancePercent,
      region = regionLabel(location = source.favorite.location),
      temperature = snapshot?.temperatureC?.formatted(unit = temperatureUnit),
      weather = snapshot?.let(weatherFactory::create),
      windKph = snapshot?.windKph?.roundToInt()
    )
  }

  private fun regionLabel(location: Location): String =
    location.admin1 ?: location.country

  private fun hourlyTemperatures(snapshot: LocationWeatherSnapshot?): ImmutableList<Float> {
    val values = snapshot?.hourlyTemperaturesC ?: return persistentListOf()
    return values.map { it.toFloat() }.toImmutableList()
  }

  private fun Double.formatted(unit: TemperatureUnit): String =
    temperatureFormatter.format(celsius = this, unit = unit)
}
