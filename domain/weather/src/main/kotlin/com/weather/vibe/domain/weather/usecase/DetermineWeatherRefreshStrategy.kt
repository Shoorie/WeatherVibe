package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.ReformatOnly
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.RegenerateSuggestion
import org.koin.core.annotation.Factory

@Factory
class DetermineWeatherRefreshStrategy {

  operator fun invoke(
    previousWeatherKey: WeatherKey?,
    currentWeatherKey: WeatherKey,
    previousSettings: UserSettings?,
    currentSettings: UserSettings
  ): WeatherRefreshStrategy = when {
    currentWeatherKey != previousWeatherKey -> RegenerateSuggestion
    currentSettings.hasBriefToneChanged(previousSettings) -> RegenerateSuggestion
    else -> ReformatOnly
  }
}
