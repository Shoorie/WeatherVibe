package com.weather.vibe.domain.settings.model

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT

data class UserSettings(
  val alertsEnabled: Boolean,
  val briefTone: BriefTone,
  val excludedGenres: Set<String>,
  val morningBriefEnabled: Boolean,
  val temperatureUnit: TemperatureUnit,
  val welcomeOnboardingSeen: Boolean
) {

  fun withAlertsEnabled(enabled: Boolean): UserSettings =
    copy(alertsEnabled = enabled)

  fun withWelcomeOnboardingSeen(): UserSettings =
    copy(welcomeOnboardingSeen = true)

  fun withBriefTone(tone: BriefTone): UserSettings =
    copy(briefTone = tone)

  fun withExcludedGenre(genre: String): UserSettings =
    copy(excludedGenres = excludedGenres + genre)

  fun withIncludedGenre(genre: String): UserSettings =
    copy(excludedGenres = excludedGenres - genre)

  fun withMorningBriefEnabled(enabled: Boolean): UserSettings =
    copy(morningBriefEnabled = enabled)

  fun withToggledTemperatureUnit(): UserSettings =
    copy(temperatureUnit = if (temperatureUnit == CELSIUS) FAHRENHEIT else CELSIUS)

  fun hasBriefToneChanged(previous: UserSettings?): Boolean =
    previous != null && briefTone != previous.briefTone
}
