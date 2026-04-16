package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import java.time.LocalDateTime

class DetectTemperatureDropAlertTest {

  private val detect = DetectTemperatureDropAlert()

  @Test
  fun `when temperature falls more than eight degrees, then alert returned`() {

    val forecast = listOf(
      hour(at = AT_NOON, temperature = 20.0),
      hour(at = AT_NOON.plusHours(3), temperature = 10.0)
    )

    val alert = detect(forecast)

    expectThat(alert?.degreesCelsius).isEqualTo(10.0)
  }

  @Test
  fun `when temperature remains stable, then no alert returned`() {

    val forecast = listOf(
      hour(at = AT_NOON, temperature = 20.0),
      hour(at = AT_NOON.plusHours(3), temperature = 19.0)
    )

    expectThat(detect(forecast)).isNull()
  }

  @Test
  fun `when forecast is empty, then no alert returned`() {

    expectThat(detect(emptyList())).isNull()
  }

  private fun hour(at: LocalDateTime, temperature: Double): HourlyWeather =
    hourlyWeather(condition = CLEAR_SKY, temperature = temperature, time = at)

  private companion object {
    val AT_NOON: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
  }
}
