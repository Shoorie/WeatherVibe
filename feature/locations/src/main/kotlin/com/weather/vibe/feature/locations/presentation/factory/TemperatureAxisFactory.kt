package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.feature.locations.presentation.state.TemperatureAxisUiState
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import org.koin.core.annotation.Factory

@Factory
internal class TemperatureAxisFactory(
  private val temperatureFormatter: TemperatureFormatter
) {

  fun create(
    firstHourlyC: List<Float>,
    secondHourlyC: List<Float>,
    temperatureUnit: TemperatureUnit
  ): TemperatureAxisUiState {
    val combined = firstHourlyC + secondHourlyC
    val padding = LocationsDefaults.TimelineAxisPadding
    val minCelsius = (combined.min() - padding).toDouble()
    val maxCelsius = (combined.max() + padding).toDouble()
    val midCelsius = (minCelsius + maxCelsius) / 2.0
    return TemperatureAxisUiState(
      min = temperatureFormatter.format(celsius = minCelsius, unit = temperatureUnit),
      mid = temperatureFormatter.format(celsius = midCelsius, unit = temperatureUnit),
      max = temperatureFormatter.format(celsius = maxCelsius, unit = temperatureUnit)
    )
  }
}
