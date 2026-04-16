package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.THUNDERSTORM
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import java.time.LocalDateTime

class DetectThunderstormAlertTest {

  private val detect = DetectThunderstormAlert()

  @Test
  fun `when forecast carries a thunderstorm, then first occurrence returned`() {

    val forecast = listOf(
      calm(at = AT_NOON),
      storm(at = AT_NOON.plusHours(2)),
      storm(at = AT_NOON.plusHours(4))
    )

    val alert = detect(forecast)

    expectThat(alert?.expectedAt).isEqualTo(AT_NOON.plusHours(2))
  }

  @Test
  fun `when forecast is calm, then no alert returned`() {

    val alert = detect(listOf(calm(at = AT_NOON)))

    expectThat(alert).isNull()
  }

  private fun calm(at: LocalDateTime): HourlyWeather =
    hourlyWeather(condition = CLEAR_SKY, time = at)

  private fun storm(at: LocalDateTime): HourlyWeather =
    hourlyWeather(condition = THUNDERSTORM, time = at)

  private companion object {
    val AT_NOON: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
  }
}
