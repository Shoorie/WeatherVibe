package com.weather.vibe.domain.settings.model

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT

data class UserSettings(
  val briefTone: BriefTone,
  val excludedGenres: Set<String>,
  val temperatureUnit: TemperatureUnit
) {

  fun withBriefTone(tone: BriefTone): UserSettings =
    copy(briefTone = tone)

  fun withExcludedGenre(genre: String): UserSettings =
    copy(excludedGenres = excludedGenres + genre)

  fun withIncludedGenre(genre: String): UserSettings =
    copy(excludedGenres = excludedGenres - genre)

  fun withToggledTemperatureUnit(): UserSettings =
    copy(temperatureUnit = if (temperatureUnit == CELSIUS) FAHRENHEIT else CELSIUS)

  fun hasBriefToneChanged(previous: UserSettings?): Boolean =
    previous != null && briefTone != previous.briefTone
}
