package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.weather.model.WeatherMetrics
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import org.koin.core.annotation.Factory

@Factory
internal class HomeStateFactory(
  private val factories: HomeFactories
) {

  fun create(
    data: WeatherData,
    metrics: WeatherMetrics,
    vibeSnapshot: WeatherSnapshot,
    unit: TemperatureUnit = CELSIUS
  ): Loaded =
    Loaded(
      details = factories.metrics.create(metrics, unit),
      forecast = factories.forecast.create(data = data, unit = unit),
      weatherSnapshot = vibeSnapshot
    )

  fun reformatTemperatures(
    current: HomeUiState,
    data: WeatherData,
    metrics: WeatherMetrics,
    unit: TemperatureUnit
  ): HomeUiState {
    val loaded = current as? Loaded ?: return current
    return loaded.copy(
      details = factories.metrics.create(metrics, unit),
      forecast = factories.forecast.create(data = data, unit = unit)
    )
  }
}
