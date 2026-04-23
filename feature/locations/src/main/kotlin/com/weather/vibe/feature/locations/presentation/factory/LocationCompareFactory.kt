package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.domain.location.model.WeatherComparison
import com.weather.vibe.feature.locations.presentation.state.LocationCardUi
import com.weather.vibe.feature.locations.presentation.state.LocationComparePair
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class LocationCompareFactory(
  private val weatherFactory: LocationWeatherFactory
) {

  fun create(
    card: LocationCardUi,
    source: FavoriteWithWeather
  ): LocationCompareUi? {
    val snapshot = source.snapshot ?: return null
    return LocationCompareUi(
      card = card,
      feelsLikeC = snapshot.feelsLikeC.roundToInt(),
      highC = snapshot.highC.roundToInt(),
      hourlyTemperatures = snapshot.hourlyTemperaturesC
        .map { it.toFloat() }
        .toImmutableList()
        .ifEmpty { persistentListOf() },
      humidityPercent = snapshot.humidityPercent,
      lowC = snapshot.lowC.roundToInt(),
      precipitationChancePercent = snapshot.precipitationChancePercent,
      temperatureC = snapshot.temperatureC.roundToInt(),
      weather = weatherFactory.create(snapshot = snapshot),
      windKph = snapshot.windKph.roundToInt()
    )
  }

  fun pairOf(
    first: LocationCompareUi,
    second: LocationCompareUi,
    winners: WeatherComparison
  ): LocationComparePair =
    LocationComparePair(first = first, second = second, winners = winners)
}
