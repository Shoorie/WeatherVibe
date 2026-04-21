package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.profile.usecase.ObserveUsername
import com.weather.vibe.domain.profile.usecase.SaveUsername
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import org.koin.core.annotation.Factory

@Factory
internal data class ProfileUseCases(
  val observeUserSettings: ObserveUserSettings,
  val observeUsername: ObserveUsername,
  val saveUsername: SaveUsername
)
