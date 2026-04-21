package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW

object WidgetSnapshotFixtures {

  const val FETCHED_AT_EPOCH_MILLIS = 1_700_000_000_000L

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
    isDay: Boolean = true,
    location: Location = WARSAW,
    mood: String = "Bright"
  ): WidgetSnapshot = WidgetSnapshot(
    condition = condition,
    currentTemperature = currentTemperature,
    fetchedAtEpochMillis = fetchedAtEpochMillis,
    isDay = isDay,
    location = location,
    mood = mood
  )
}
