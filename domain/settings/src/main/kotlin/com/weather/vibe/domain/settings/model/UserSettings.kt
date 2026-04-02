package com.weather.vibe.domain.settings.model

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT

data class UserSettings(
  val briefTone: BriefTone,
  val excludedGenres: Set<String>,
  val temperatureUnit: TemperatureUnit
) {

  val excludedGenresText: String
    get() = excludedGenres.sorted()
      .joinToString(separator = GENRES_SEPARATOR)

  fun withBriefTone(tone: BriefTone): UserSettings =
    copy(briefTone = tone)

  fun affectsWeatherSuggestion(previous: UserSettings?): Boolean =
    previous != null && (hasBriefToneChanged(previous) || hasExcludedGenresChanged(previous))

  fun withExcludedGenres(genres: Set<String>): UserSettings =
    copy(excludedGenres = genres)

  fun withToggledTemperatureUnit(): UserSettings =
    copy(temperatureUnit = if (temperatureUnit == CELSIUS) FAHRENHEIT else CELSIUS)

  private fun hasBriefToneChanged(previous: UserSettings): Boolean =
    briefTone != previous.briefTone

  private fun hasExcludedGenresChanged(previous: UserSettings): Boolean =
    excludedGenres != previous.excludedGenres

  private companion object {
    const val GENRES_SEPARATOR = ","
  }
}
