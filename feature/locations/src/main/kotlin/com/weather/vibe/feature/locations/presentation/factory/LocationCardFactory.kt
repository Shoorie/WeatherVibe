package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.feature.locations.presentation.state.LocationCardUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class LocationCardFactory(
  private val weatherFactory: LocationWeatherFactory
) {

  fun create(source: FavoriteWithWeather): LocationCardUi {
    val snapshot = source.snapshot
    return LocationCardUi(
      favoriteId = source.favorite.id,
      feelsLikeC = snapshot?.feelsLikeC?.roundToInt(),
      highC = snapshot?.highC?.roundToInt(),
      hourlyTemperatures = hourlyTemperatures(snapshot = snapshot),
      humidityPercent = snapshot?.humidityPercent,
      label = source.favorite.label,
      locationId = source.favorite.location.id,
      lowC = snapshot?.lowC?.roundToInt(),
      name = source.favorite.location.name,
      precipitationChancePercent = snapshot?.precipitationChancePercent,
      region = regionLabel(location = source.favorite.location),
      temperatureC = snapshot?.temperatureC?.roundToInt(),
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
}
