package com.weather.vibe.domain.settings.model

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT

data class UserSettings(
  val briefTone: BriefTone,
  val excludedGenres: Set<String>,
  val moodReminderEnabled: Boolean,
  val morningBriefEnabled: Boolean,
  val pollenAlertsEnabled: Boolean,
  val temperatureUnit: TemperatureUnit,
  val weatherAlertsEnabled: Boolean,
  val welcomeOnboardingSeen: Boolean,
  val widgetPromoEligible: Boolean,
  val widgetPromoSeen: Boolean
) {

  fun withBriefTone(tone: BriefTone): UserSettings =
    copy(briefTone = tone)

  fun withExcludedGenre(genre: String): UserSettings =
    copy(excludedGenres = excludedGenres + genre)

  fun withIncludedGenre(genre: String): UserSettings =
    copy(excludedGenres = excludedGenres - genre)

  fun withMoodReminderEnabled(enabled: Boolean): UserSettings =
    copy(moodReminderEnabled = enabled)

  fun withMorningBriefEnabled(enabled: Boolean): UserSettings =
    copy(morningBriefEnabled = enabled)

  fun withPollenAlertsEnabled(enabled: Boolean): UserSettings =
    copy(pollenAlertsEnabled = enabled)

  fun withToggledTemperatureUnit(): UserSettings =
    copy(temperatureUnit = if (temperatureUnit == CELSIUS) FAHRENHEIT else CELSIUS)

  fun withWeatherAlertsEnabled(enabled: Boolean): UserSettings =
    copy(weatherAlertsEnabled = enabled)

  fun withWelcomeOnboardingSeen(): UserSettings =
    copy(welcomeOnboardingSeen = true)

  fun withWidgetPromoEligible(): UserSettings =
    copy(widgetPromoEligible = true)

  fun withWidgetPromoSeen(): UserSettings =
    copy(widgetPromoSeen = true)

  fun hasBriefToneChanged(previous: UserSettings?): Boolean =
    previous != null && briefTone != previous.briefTone
}
