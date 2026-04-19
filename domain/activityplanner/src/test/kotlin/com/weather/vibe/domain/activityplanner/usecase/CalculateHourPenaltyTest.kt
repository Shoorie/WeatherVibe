package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPreferences.Companion.forActivity
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan

class CalculateHourPenaltyTest {

  private val calculateHourPenalty = CalculateHourPenalty()
  private val runningPreferences = forActivity(ActivityType.RUNNING)

  @Test
  fun `given optimal conditions, then zero penalty returned`() {

    val hour = hourlyWeather(
      temperature = 15.0,
      precipitationProbability = 0,
      windSpeed = 5.0
    )

    val penalty = calculateHourPenalty(
      hour = hour,
      uvIndex = 2.0,
      preferences = runningPreferences
    )

    expectThat(penalty).isEqualTo(0)
  }

  @Test
  fun `given temperature above tolerance, then extreme penalty returned`() {

    val hour = hourlyWeather(temperature = 35.0)

    val penalty = calculateHourPenalty(
      hour = hour,
      uvIndex = 0.0,
      preferences = runningPreferences
    )

    expectThat(penalty).isGreaterThan(35)
  }

  @Test
  fun `given strong wind, then wind penalty added`() {

    val calmHour = hourlyWeather(temperature = 15.0, windSpeed = 5.0)
    val windyHour = hourlyWeather(temperature = 15.0, windSpeed = 50.0)

    val calmPenalty = calculateHourPenalty(
      hour = calmHour,
      uvIndex = 0.0,
      preferences = runningPreferences
    )

    val windyPenalty = calculateHourPenalty(
      hour = windyHour,
      uvIndex = 0.0,
      preferences = runningPreferences
    )

    expectThat(windyPenalty).isGreaterThan(calmPenalty)
  }

  @Test
  fun `given high uv, then uv penalty added`() {

    val hour = hourlyWeather(temperature = 15.0)

    val safeUvPenalty = calculateHourPenalty(
      hour = hour,
      uvIndex = 2.0,
      preferences = runningPreferences
    )

    val highUvPenalty = calculateHourPenalty(
      hour = hour,
      uvIndex = 10.0,
      preferences = runningPreferences
    )

    expectThat(highUvPenalty).isGreaterThan(safeUvPenalty)
  }

  @Test
  fun `given high precipitation probability, then precipitation penalty added`() {

    val dryHour = hourlyWeather(temperature = 15.0, precipitationProbability = 10)
    val wetHour = hourlyWeather(temperature = 15.0, precipitationProbability = 90)

    val dryPenalty = calculateHourPenalty(
      hour = dryHour,
      uvIndex = 0.0,
      preferences = runningPreferences
    )

    val wetPenalty = calculateHourPenalty(
      hour = wetHour,
      uvIndex = 0.0,
      preferences = runningPreferences
    )

    expectThat(wetPenalty).isGreaterThan(dryPenalty)
  }
}
