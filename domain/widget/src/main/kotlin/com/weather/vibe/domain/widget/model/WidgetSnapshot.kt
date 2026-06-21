package com.weather.vibe.domain.widget.model

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.vibe.model.VibeMood
import com.weather.vibe.domain.weather.model.WeatherCondition

data class WidgetSnapshot(
  val aiMood: String?,
  val condition: WeatherCondition,
  val currentTemperature: Double,
  val fetchedAtEpochMillis: Long,
  val isDay: Boolean,
  val location: Location,
  val vibeMood: VibeMood
)
