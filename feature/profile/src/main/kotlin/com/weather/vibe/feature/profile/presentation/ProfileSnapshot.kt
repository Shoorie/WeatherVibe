package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.profile.model.ProfileSummary
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.viberating.model.VibeOverview

internal data class ProfileSnapshot(
  val favoritesCountResult: Result<Int>,
  val profile: ProfileSummary,
  val settingsResult: Result<UserSettings>,
  val themeMode: ThemeMode,
  val vibeOverview: VibeOverview
)
