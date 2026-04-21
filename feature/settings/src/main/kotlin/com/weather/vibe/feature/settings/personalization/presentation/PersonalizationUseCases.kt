package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.domain.settings.usecase.GetAvailableBriefTones
import com.weather.vibe.domain.settings.usecase.IncludeGenre
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SelectBriefTone
import com.weather.vibe.domain.settings.usecase.ToggleTemperatureUnit
import org.koin.core.annotation.Factory

@Factory
internal data class PersonalizationUseCases(
  val getAvailableBriefTones: GetAvailableBriefTones,
  val includeGenre: IncludeGenre,
  val observeUserSettings: ObserveUserSettings,
  val selectBriefTone: SelectBriefTone,
  val toggleTemperatureUnit: ToggleTemperatureUnit
)
