package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import java.time.LocalDateTime

class DetectHeavyRainAlertTest {

  private val detect = DetectHeavyRainAlert()

  @Test
  fun `when hourly rain exceeds heavy threshold, then alert carries millimetres`() {

    val forecast = listOf(rain(at = AT_NOON, millimetres = 6.8))

    val alert = detect(forecast)

    expectThat(alert?.millimetres).isEqualTo(6.8)
  }

  @Test
  fun `when drizzle stays below heavy threshold, then no alert returned`() {

    val forecast = listOf(rain(at = AT_NOON, millimetres = 1.0))

    expectThat(detect(forecast)).isNull()
  }

  private fun rain(at: LocalDateTime, millimetres: Double): HourlyWeather =
    HourlyWeather(
      condition = RAIN,
      humidity = 70,
      precipitation = millimetres,
      precipitationProbability = 90,
      temperature = 14.0,
      time = at,
      windSpeed = 10.0
    )

  private companion object {
    val AT_NOON: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
  }
}
