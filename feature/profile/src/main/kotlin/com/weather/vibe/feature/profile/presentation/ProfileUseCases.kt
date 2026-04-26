package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.appearance.usecase.ObserveThemeMode
import com.weather.vibe.domain.appearance.usecase.SetThemeMode
import com.weather.vibe.domain.location.usecase.ObserveLocationFavoritesCount
import com.weather.vibe.domain.profile.usecase.ObserveProfile
import com.weather.vibe.domain.profile.usecase.SaveUsername
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.viberating.usecase.ObserveVibeOverview
import org.koin.core.annotation.Factory

@Factory
internal data class ProfileUseCases(
  val observeFavoritesCount: ObserveLocationFavoritesCount,
  val observeProfile: ObserveProfile,
  val observeThemeMode: ObserveThemeMode,
  val observeUserSettings: ObserveUserSettings,
  val observeVibeOverview: ObserveVibeOverview,
  val saveUsername: SaveUsername,
  val setThemeMode: SetThemeMode
)
