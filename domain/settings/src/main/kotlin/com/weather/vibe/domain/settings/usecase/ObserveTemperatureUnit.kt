package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveTemperatureUnit internal constructor(
  private val observeUserSettings: ObserveUserSettings
) {

  operator fun invoke(): Flow<TemperatureUnit> =
    observeUserSettings()
      .map { it.getOrNull()?.temperatureUnit ?: CELSIUS }
      .distinctUntilChanged()
}
