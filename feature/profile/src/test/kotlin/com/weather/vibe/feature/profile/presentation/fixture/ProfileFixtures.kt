package com.weather.vibe.feature.profile.presentation.fixture

import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.domain.profile.model.ProfileSummary
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.viberating.model.VibeOverview
import com.weather.vibe.feature.profile.presentation.ProfileSnapshot
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import java.util.Locale

internal object ProfileFixtures {

  const val UNNAMED_GREETING = "Hey"
  const val UNNAMED_SUBTITLE = "Tap"
  const val RETURNING_SUBTITLE = "Glad to see you again"
  const val UNNAMED_AVATAR = "?"
  const val LOCATIONS_LABEL = "Locations"
  const val LOCATIONS_EMOJI = "PIN"
  const val LOCATIONS_CLICK_LABEL = "Open locations"
  const val MORNING_BRIEF_LABEL = "Morning brief"
  const val MORNING_BRIEF_EMOJI = "SUN"
  const val MORNING_BRIEF_CLICK_LABEL = "Open notifications"
  const val ALERTS_LABEL = "Alerts"
  const val ALERTS_EMOJI = "BELL"
  const val ALERTS_CLICK_LABEL = "Open notifications"
  const val STATUS_ON = "On"
  const val STATUS_OFF = "Off"
  const val TONE_LABEL_WITTY = "Witty"
  const val TONE_LABEL_FORMAL = "Formal"
  const val TONE_LABEL_HUMOROUS = "Humorous"
  const val USERNAME_JOHN = "John"

  const val VIBE_TITLE = "Your vibe"
  const val VIBE_EMPTY_CTA = "Rate your first day"
  const val VIBE_LOADED_CLICK_LABEL = "Open vibe history"
  const val VIBE_EMPTY_CLICK_LABEL = "Rate your first day"

  const val APPEARANCE_TITLE = "Appearance"
  const val APPEARANCE_BODY = "Light, dark or system"
  const val APPEARANCE_LIGHT = "Light"
  const val APPEARANCE_AUTO = "Auto"
  const val APPEARANCE_DARK = "Dark"

  fun greeting(username: String): String =
    "Hi, $username"

  fun vibeAverageLabel(value: Double): String =
    "${"%.1f".format(Locale.ROOT, value)}/5"

  fun vibeStreakLabel(days: Int): String =
    "$days days in a row"

  fun appearanceOptionLabel(mode: ThemeMode): String = when (mode) {
    LIGHT -> APPEARANCE_LIGHT
    AUTO -> APPEARANCE_AUTO
    DARK -> APPEARANCE_DARK
  }

  fun profileSummary(
    username: String = USERNAME_JOHN,
    usageDays: Int = 1
  ): ProfileSummary =
    ProfileSummary(
      username = username,
      usageDays = usageDays
    )

  fun profileSnapshot(
    favoritesCount: Int = 0,
    favoritesCountResult: Result<Int> = Result.success(favoritesCount),
    profile: ProfileSummary = profileSummary(),
    settings: UserSettings = userSettings(),
    settingsResult: Result<UserSettings> = Result.success(settings),
    themeMode: ThemeMode = AUTO,
    vibeOverview: VibeOverview = VibeOverview.EMPTY
  ): ProfileSnapshot =
    ProfileSnapshot(
      favoritesCountResult = favoritesCountResult,
      profile = profile,
      settingsResult = settingsResult,
      themeMode = themeMode,
      vibeOverview = vibeOverview
    )

  fun toneLabel(tone: BriefTone): String = when (tone) {
    WITTY_AND_FRIENDLY -> TONE_LABEL_WITTY
    FORMAL -> TONE_LABEL_FORMAL
    HUMOROUS -> TONE_LABEL_HUMOROUS
  }
}
