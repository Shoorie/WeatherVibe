package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.AreAlertsEnabled
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.POOR_AIR_QUALITY
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.THUNDERSTORM
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class GatherWeatherAlertsTest {

  private val areAlertsEnabled = mockk<AreAlertsEnabled>()
  private val deduplicator = AlertDeduplicator()
  private val detectAqiAlert = mockk<DetectAqiAlert>()
  private val detectWeatherAlerts = mockk<DetectWeatherAlerts>()
  private val getAirQuality = mockk<GetAirQuality>()
  private val getWeather = mockk<GetWeather>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val gather = GatherWeatherAlerts(
    alertDeduplicator = deduplicator,
    areAlertsEnabled = areAlertsEnabled,
    detectAqiAlert = detectAqiAlert,
    detectWeatherAlerts = detectWeatherAlerts,
    getAirQuality = getAirQuality,
    getWeather = getWeather,
    observeCurrentLocation = observeCurrentLocation
  )

  @Before
  fun setUp() {
    coEvery { areAlertsEnabled() } returns true
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { getWeather(WARSAW.toCoordinates()) } returns flowOf(success(WEATHER))
    every { detectWeatherAlerts(WEATHER) } returns listOf(THUNDERSTORM)
    coEvery { getAirQuality(WARSAW.toCoordinates()) } returns AirQualityFixtures.POOR
    every { detectAqiAlert(any()) } returns null
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when invoked, then weather alerts returned`() = runTest {

    val alerts = gather()

    expectThat(alerts).contains(THUNDERSTORM)
  }

  @Test
  fun `when air quality alert produced, then included in result`() = runTest {

    every { detectAqiAlert(AirQualityFixtures.POOR) } returns POOR_AIR_QUALITY

    val alerts = gather()

    expectThat(alerts).hasSize(2).contains(POOR_AIR_QUALITY)
  }

  @Test
  fun `given air quality fetch fails, when invoked, then weather alerts still returned`() = runTest {

    coEvery { getAirQuality(WARSAW.toCoordinates()) } throws IllegalStateException("offline")

    val alerts = gather()

    expectThat(alerts).contains(THUNDERSTORM)
  }

  @Test
  fun `given alerts disabled, when invoked, then empty list returned`() = runTest {

    coEvery { areAlertsEnabled() } returns false

    expectThat(gather()).isEmpty()
  }

  @Test
  fun `given no current location, when invoked, then empty list returned`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    expectThat(gather()).isEmpty()
  }

  @Test
  fun `given weather fetch fails, when invoked, then error propagates`() = runTest {

    every { getWeather(WARSAW.toCoordinates()) } returns
      flowOf(failure(IllegalStateException("offline")))

    expectThrows<IllegalStateException> { gather() }
  }

  @Test
  fun `given alert already notified, when invoked again, then filtered out`() = runTest {

    gather()

    val secondCall = gather()

    expectThat(secondCall).isEmpty()
  }
}
