package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.location.usecase.ObserveLocationFavoritesCount
import com.weather.vibe.domain.profile.usecase.ObserveProfile
import com.weather.vibe.domain.profile.usecase.SaveUsername
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import org.koin.core.annotation.Factory

@Factory
internal data class ProfileUseCases(
  val observeFavoritesCount: ObserveLocationFavoritesCount,
  val observeProfile: ObserveProfile,
  val observeUserSettings: ObserveUserSettings,
  val saveUsername: SaveUsername
)
