package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherMetrics
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import org.koin.core.annotation.Factory

@Factory
internal class HomeStateFactory(
  private val factories: HomeFactories,
  private val getCurrentWeatherMetrics: GetCurrentWeatherMetrics
) {

  fun create(data: WeatherData, unit: TemperatureUnit = CELSIUS): Loaded =
    Loaded(
      details = factories.metrics.create(getCurrentWeatherMetrics(data), unit),
      forecast = factories.forecast.create(data = data, unit = unit)
    )

  fun reformatTemperatures(
    current: HomeUiState,
    data: WeatherData,
    unit: TemperatureUnit
  ): HomeUiState {
    val loaded = current as? Loaded ?: return current
    return create(data, unit).copy(
      aiSuggestion = loaded.aiSuggestion,
      alert = loaded.alert,
      dailyVibe = loaded.dailyVibe
    )
  }
}
