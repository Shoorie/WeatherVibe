package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.vibe.model.VibeMood
import com.weather.vibe.domain.vibe.model.VibeMood.OKAY
import com.weather.vibe.domain.vibe.model.VibeMood.PLEASANT
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW

object WidgetSnapshotFixtures {

  const val FETCHED_AT_EPOCH_MILLIS = 1_700_000_000_000L

  val SNAPSHOT: WidgetSnapshot = snapshot()

  val RAINY_SNAPSHOT: WidgetSnapshot = snapshot(
    aiMood = "Cozy",
    condition = RAIN,
    currentTemperature = 9.0,
    vibeMood = OKAY
  )

  fun snapshot(
    aiMood: String? = "Bright",
    condition: WeatherCondition = CLEAR_SKY,
    currentTemperature: Double = 18.0,
    fetchedAtEpochMillis: Long = FETCHED_AT_EPOCH_MILLIS,
    isDay: Boolean = true,
    location: Location = WARSAW,
    vibeMood: VibeMood = PLEASANT
  ): WidgetSnapshot = WidgetSnapshot(
    aiMood = aiMood,
    condition = condition,
    currentTemperature = currentTemperature,
    fetchedAtEpochMillis = fetchedAtEpochMillis,
    isDay = isDay,
    location = location,
    vibeMood = vibeMood
  )
}
