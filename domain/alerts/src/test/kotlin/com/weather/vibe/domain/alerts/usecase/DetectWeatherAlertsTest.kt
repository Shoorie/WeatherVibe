package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.model.WeatherAlert.HeavyRainImminent
import com.weather.vibe.domain.alerts.model.WeatherAlert.SharpTemperatureDrop
import com.weather.vibe.domain.alerts.model.WeatherAlert.ThunderstormImminent
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEmpty
import java.time.LocalDateTime

class DetectWeatherAlertsTest {

  private val detectHeavyRainAlert = mockk<DetectHeavyRainAlert>()
  private val detectTemperatureDropAlert = mockk<DetectTemperatureDropAlert>()
  private val detectThunderstormAlert = mockk<DetectThunderstormAlert>()
  private val timeProvider = FakeTimeProvider(current = NOW)
  private val detect = DetectWeatherAlerts(
    detectHeavyRainAlert = detectHeavyRainAlert,
    detectTemperatureDropAlert = detectTemperatureDropAlert,
    detectThunderstormAlert = detectThunderstormAlert,
    timeProvider = timeProvider
  )

  @Before
  fun setUp() {
    every { detectHeavyRainAlert(any()) } returns null
    every { detectTemperatureDropAlert(any()) } returns null
    every { detectThunderstormAlert(any()) } returns null
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when forecast window is empty, then no alerts returned`() {

    val alerts = detect(weatherData(hourlyForecast = emptyList()))

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `when forecast falls outside lookahead, then no alerts returned`() {

    val forecast = listOf(calm(at = NOW.plusHours(10)))

    val alerts = detect(weatherData(hourlyForecast = forecast))

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `when sub-detectors produce alerts, then all combined in result`() {

    val forecast = listOf(calm(at = NOW.plusHours(1)))
    every { detectThunderstormAlert(any()) } returns STORM
    every { detectHeavyRainAlert(any()) } returns RAIN
    every { detectTemperatureDropAlert(any()) } returns DROP

    val alerts = detect(weatherData(hourlyForecast = forecast))

    expectThat(alerts).containsExactlyInAnyOrder(STORM, RAIN, DROP)
  }

  private fun calm(at: LocalDateTime): HourlyWeather =
    hourlyWeather(condition = CLEAR_SKY, time = at)

  private companion object {
    val NOW: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
    val STORM = ThunderstormImminent(expectedAt = NOW.plusHours(2))
    val RAIN = HeavyRainImminent(expectedAt = NOW.plusHours(3), millimetres = 6.0)
    val DROP = SharpTemperatureDrop(expectedAt = NOW.plusHours(4), degreesCelsius = 9.0)
  }
}
