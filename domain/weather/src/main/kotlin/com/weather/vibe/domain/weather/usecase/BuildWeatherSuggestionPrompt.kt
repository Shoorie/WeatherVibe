package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.UserDispositionEntry

interface BuildWeatherSuggestionPrompt {

  operator fun invoke(
    condition: SimplifiedCondition,
    excludedGenres: Set<String>,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay,
    todayDispositionEntries: List<UserDispositionEntry>,
    tone: BriefTone
  ): String
}
