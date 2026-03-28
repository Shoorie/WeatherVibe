package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.Persona
import com.weather.vibe.domain.settings.model.Persona.WITTY
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.weather.model.WeatherData

internal data class HomeSnapshot(
  val excludedGenres: String = "",
  val persona: Persona = WITTY,
  val temperatureUnit: TemperatureUnit = CELSIUS,
  val weatherData: WeatherData? = null
) {

  fun hasAiSettingsChange(settings: UserSettings): Boolean =
    persona != settings.persona ||
      excludedGenres != settings.excludedGenres

  fun hasTemperatureChange(settings: UserSettings): Boolean =
    temperatureUnit != settings.temperatureUnit

  fun withSettings(settings: UserSettings): HomeSnapshot =
    copy(
      excludedGenres = settings.excludedGenres,
      persona = settings.persona,
      temperatureUnit = settings.temperatureUnit
    )
}
