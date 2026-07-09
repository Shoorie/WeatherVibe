package com.weather.vibe.domain.weather.model

import com.weather.vibe.domain.settings.model.BriefTone
import java.time.LocalDate

data class WeatherSuggestionPromptInput(
  val condition: SimplifiedCondition,
  val currentDate: LocalDate,
  val excludedGenres: Set<String>,
  val locationName: String,
  val temperatureCelsius: Double,
  val timeOfDay: TimeOfDay,
  val todayDispositionEntries: List<UserDispositionEntry>,
  val tone: BriefTone
)
