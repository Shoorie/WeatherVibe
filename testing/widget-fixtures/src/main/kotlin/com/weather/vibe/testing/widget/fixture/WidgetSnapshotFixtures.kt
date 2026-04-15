package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
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

  val SNAPSHOT: WidgetSnapshot = snapshot()

  val RAINY_SNAPSHOT: WidgetSnapshot = snapshot(
    condition = RAIN,
    currentTemperature = 9.0,
    mood = "Cozy"
  )

  fun snapshot(
    condition: WeatherCondition = CLEAR_SKY,
    currentTemperature: Double = 18.0,
    fetchedAtEpochMillis: Long = FETCHED_AT_EPOCH_MILLIS,
    location: Location = DEFAULT_LOCATION,
    mood: String = "Bright"
  ): WidgetSnapshot = WidgetSnapshot(
    condition = condition,
    currentTemperature = currentTemperature,
    fetchedAtEpochMillis = fetchedAtEpochMillis,
    location = location,
    mood = mood
  )
}
