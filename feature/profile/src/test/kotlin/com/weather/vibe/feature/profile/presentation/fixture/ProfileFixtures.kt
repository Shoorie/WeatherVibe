package com.weather.vibe.feature.profile.presentation.fixture

import com.weather.vibe.domain.profile.model.ProfileSummary
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY

internal object ProfileFixtures {

  const val UNNAMED_GREETING = "Hey"
  const val UNNAMED_SUBTITLE = "Tap"
  const val UNNAMED_AVATAR = "?"
  const val LOCATIONS_LABEL = "Locations"
  const val LOCATIONS_CLICK_LABEL = "Open locations"
  const val MORNING_BRIEF_LABEL = "Morning brief"
  const val MORNING_BRIEF_CLICK_LABEL = "Open notifications"
  const val ALERTS_LABEL = "Alerts"
  const val ALERTS_CLICK_LABEL = "Open notifications"
  const val STATUS_ON = "On"
  const val STATUS_OFF = "Off"
  const val TONE_LABEL_WITTY = "Witty"
  const val TONE_LABEL_FORMAL = "Formal"
  const val TONE_LABEL_HUMOROUS = "Humorous"
  const val QUOTE_WITTY = "Quote witty"
  const val QUOTE_FORMAL = "Quote formal"
  const val QUOTE_HUMOROUS = "Quote humorous"
  const val USERNAME_JOHN = "John"

  fun greeting(username: String): String =
    "Hi, $username"

  fun days(days: Int): String =
    "$days days"

  fun profileSummary(
    username: String = USERNAME_JOHN,
    usageDays: Int = 1
  ): ProfileSummary =
    ProfileSummary(
      username = username,
      usageDays = usageDays
    )

  fun toneLabel(tone: BriefTone): String = when (tone) {
    WITTY_AND_FRIENDLY -> TONE_LABEL_WITTY
    FORMAL -> TONE_LABEL_FORMAL
    HUMOROUS -> TONE_LABEL_HUMOROUS
  }

  fun quote(tone: BriefTone): String = when (tone) {
    WITTY_AND_FRIENDLY -> QUOTE_WITTY
    FORMAL -> QUOTE_FORMAL
    HUMOROUS -> QUOTE_HUMOROUS
  }
}
