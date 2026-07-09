package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import java.time.LocalDate

interface BuildWeatherSuggestionPrompt {

  operator fun invoke(
    condition: SimplifiedCondition,
    currentDate: LocalDate,
    excludedGenres: Set<String>,
    locationName: String,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay,
    tone: BriefTone
  ): String
}
