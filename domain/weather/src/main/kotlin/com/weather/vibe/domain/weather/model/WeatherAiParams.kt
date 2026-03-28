package com.weather.vibe.domain.weather.model

import com.weather.vibe.domain.settings.model.Persona
import java.time.LocalDate

data class WeatherAiParams(
  val cityName: String,
  val date: LocalDate,
  val excludedGenres: String,
  val persona: Persona
)
