package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.location.model.LocationWeatherComparison
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationComparePairUiState
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUiState
import com.weather.vibe.feature.locations.presentation.state.TemperatureAxisUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class LocationCompareFactory(
  private val temperatureFormatter: TemperatureFormatter,
  private val weatherFactory: LocationWeatherFactory
) {

  fun create(
    card: LocationCardUiState,
    source: LocationFavoriteWithWeather,
    temperatureUnit: TemperatureUnit
  ): LocationCompareUiState? {
    val snapshot = source.snapshot ?: return null
    return LocationCompareUiState(
      card = card,
      feelsLike = snapshot.feelsLikeC.formatted(unit = temperatureUnit),
      high = snapshot.highC.formatted(unit = temperatureUnit),
      hourlyTemperatures = snapshot.hourlyTemperaturesC
        .map { it.toFloat() }
        .toImmutableList()
        .ifEmpty { persistentListOf() },
      humidityPercent = snapshot.humidityPercent,
      low = snapshot.lowC.formatted(unit = temperatureUnit),
      precipitationChancePercent = snapshot.precipitationChancePercent,
      temperature = snapshot.temperatureC.formatted(unit = temperatureUnit),
      weather = weatherFactory.create(snapshot = snapshot),
      windKph = snapshot.windKph.roundToInt()
    )
  }

  fun pairOf(
    first: LocationCompareUiState,
    second: LocationCompareUiState,
    winners: LocationWeatherComparison,
    temperatureAxis: TemperatureAxisUiState
  ): LocationComparePairUiState =
    LocationComparePairUiState(
      first = first,
      second = second,
      winners = winners,
      temperatureAxis = temperatureAxis
    )

  private fun Double.formatted(unit: TemperatureUnit): String =
    temperatureFormatter.format(celsius = this, unit = unit)
}
