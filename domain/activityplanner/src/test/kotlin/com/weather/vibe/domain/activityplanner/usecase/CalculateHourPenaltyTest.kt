package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPreferences.Companion.forActivity
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isGreaterThanOrEqualTo
import java.time.LocalDateTime

class CalculateHourPenaltyTest {

  private val calculateHourPenalty = CalculateHourPenalty()
  private val runningPreferences = forActivity(ActivityType.RUNNING)

  @Test
  fun `when conditions optimal, then zero penalty returned`() {

    val penalty = calculateHourPenalty(
      hour = hourlyWeather(
        temperature = 15.0,
        precipitationProbability = 0,
        windSpeed = 5.0,
        time = NOON
      ),
      uvIndex = 2.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(penalty).isEqualTo(0)
  }

  @Test
  fun `when apparent temperature above tolerance, then extreme penalty returned`() {

    val penalty = calculateHourPenalty(
      hour = hourlyWeather(apparentTemperature = 35.0, temperature = 28.0, time = NOON),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(penalty).isGreaterThan(35)
  }

  @Test
  fun `when wind strong, then wind penalty added`() {

    val calmPenalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, windSpeed = 5.0, time = NOON),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    val windyPenalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, windSpeed = 50.0, time = NOON),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(windyPenalty).isGreaterThan(calmPenalty)
  }

  @Test
  fun `when uv high, then uv penalty added`() {

    val safePenalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, time = NOON),
      uvIndex = 2.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    val highPenalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, time = NOON),
      uvIndex = 10.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(highPenalty).isGreaterThan(safePenalty)
  }

  @Test
  fun `when precipitation probability high, then precipitation penalty added`() {

    val dryPenalty = calculateHourPenalty(
      hour = hourlyWeather(
        temperature = 15.0,
        precipitationProbability = 10,
        time = NOON
      ),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    val wetPenalty = calculateHourPenalty(
      hour = hourlyWeather(
        temperature = 15.0,
        precipitationProbability = 90,
        time = NOON
      ),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(wetPenalty).isGreaterThan(dryPenalty)
  }

  @Test
  fun `when wind gusts exceed threshold, then gusts penalty added`() {

    val calmPenalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, windGusts = 10.0, time = NOON),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    val gustyPenalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, windGusts = 70.0, time = NOON),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(gustyPenalty).isGreaterThan(calmPenalty)
  }

  @Test
  fun `when hour before sunrise, then daylight penalty added`() {

    val penalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, time = PRE_DAWN),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(penalty).isGreaterThanOrEqualTo(runningPreferences.daylightPenalty)
  }

  @Test
  fun `when hour after sunset, then daylight penalty added`() {

    val penalty = calculateHourPenalty(
      hour = hourlyWeather(temperature = 15.0, time = LATE_NIGHT),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(penalty).isGreaterThanOrEqualTo(runningPreferences.daylightPenalty)
  }

  @Test
  fun `given sunrise unknown, when hour scored, then no daylight penalty added`() {

    val penalty = calculateHourPenalty(
      hour = hourlyWeather(
        temperature = 15.0,
        precipitationProbability = 0,
        windSpeed = 5.0,
        time = LATE_NIGHT
      ),
      uvIndex = 0.0,
      sunrise = null,
      sunset = null,
      preferences = runningPreferences
    )

    expectThat(penalty).isEqualTo(0)
  }

  @Test
  fun `when condition is thunderstorm, then blocked penalty returned`() {

    val penalty = calculateHourPenalty(
      hour = hourlyWeather(
        condition = WeatherCondition.THUNDERSTORM,
        temperature = 15.0,
        time = NOON
      ),
      uvIndex = 0.0,
      sunrise = SUNRISE,
      sunset = SUNSET,
      preferences = runningPreferences
    )

    expectThat(penalty).isEqualTo(BLOCKED)
  }

  private companion object {
    const val BLOCKED = 100
    val PRE_DAWN: LocalDateTime = LocalDateTime.of(2026, 4, 8, 4, 0)
    val SUNRISE: LocalDateTime = LocalDateTime.of(2026, 4, 8, 6, 0)
    val NOON: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
    val SUNSET: LocalDateTime = LocalDateTime.of(2026, 4, 8, 18, 0)
    val LATE_NIGHT: LocalDateTime = LocalDateTime.of(2026, 4, 8, 23, 0)
  }
}
