package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.widget.model.WidgetSnapshot

object WidgetSnapshotFixtures {

  const val FETCHED_AT_EPOCH_MILLIS = 1_700_000_000_000L

  val DEFAULT_LOCATION: Location = Location(
    id = 1L,
    name = "Warsaw",
    admin1 = "Mazowieckie",
    country = "Poland",
    latitude = 52.23,
    longitude = 21.01
  )

  val DEFAULT_SUGGESTION: WeatherSuggestion = WeatherSuggestion(
    briefText = "Sunny day, perfect for a walk.",
    genres = listOf("pop", "indie"),
    mood = "Bright",
    moodDescription = "Uplifting energy to match the sky"
  )

  val SNAPSHOT: WidgetSnapshot = snapshot()

  val RAINY_SNAPSHOT: WidgetSnapshot = snapshot(
    condition = RAIN,
    currentTemperature = 9.0,
    isDay = false,
    suggestion = DEFAULT_SUGGESTION.copy(
      briefText = "Grab an umbrella, it's pouring.",
      mood = "Cozy"
    )
  )

  fun snapshot(
    condition: WeatherCondition = CLEAR_SKY,
    currentTemperature: Double = 18.0,
    fetchedAtEpochMillis: Long = FETCHED_AT_EPOCH_MILLIS,
    isDay: Boolean = true,
    location: Location = DEFAULT_LOCATION,
    suggestion: WeatherSuggestion = DEFAULT_SUGGESTION
  ): WidgetSnapshot = WidgetSnapshot(
    condition = condition,
    currentTemperature = currentTemperature,
    fetchedAtEpochMillis = fetchedAtEpochMillis,
    isDay = isDay,
    location = location,
    suggestion = suggestion
  )
}
