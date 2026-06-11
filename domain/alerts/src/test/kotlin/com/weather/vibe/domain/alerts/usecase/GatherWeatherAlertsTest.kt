package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.alerts.fake.FakeAlertNotificationLog
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.POOR
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.HIGH_UV_INDEX
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
import strikt.assertions.contains
import strikt.assertions.isEmpty
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class GatherWeatherAlertsTest {

  private val deduplicator = AlertDeduplicator(FakeAlertNotificationLog())
  private val detectAqiAlert = mockk<DetectAqiAlert>()
  private val detectUvAlert = mockk<DetectUvAlert>()
  private val detectWeatherAlerts = mockk<DetectWeatherAlerts>()
  private val getAirQuality = mockk<GetAirQuality>()
  private val getWeather = mockk<GetWeather>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val gatherWeatherAlerts = GatherWeatherAlerts(
    alertDeduplicator = deduplicator,
    detectAqiAlert = detectAqiAlert,
    detectUvAlert = detectUvAlert,
    detectWeatherAlerts = detectWeatherAlerts,
    getAirQuality = getAirQuality,
    getWeather = getWeather,
    observeCurrentLocation = observeCurrentLocation
  )

  @Before
  fun setUp() {
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { getWeather(WARSAW.toCoordinates()) } returns flowOf(success(WEATHER))
    every { detectWeatherAlerts(WEATHER) } returns listOf(THUNDERSTORM)
    coEvery { getAirQuality(WARSAW.toCoordinates()) } returns success(POOR)
    every { detectAqiAlert(any()) } returns null
    every { detectUvAlert(any()) } returns null
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when alerts gathered, then detected weather alert returned`() = runTest {

    val alerts = gatherWeatherAlerts()

    expectThat(alerts).contains(THUNDERSTORM)
  }

  @Test
  fun `given air quality alert detected, when alerts gathered, then air quality alert returned`() =
    runTest {

      every { detectAqiAlert(POOR) } returns POOR_AIR_QUALITY

      val alerts = gatherWeatherAlerts()

      expectThat(alerts).contains(POOR_AIR_QUALITY)
    }

  @Test
  fun `given uv alert detected, when alerts gathered, then uv alert returned`() = runTest {

    every { detectUvAlert(WEATHER) } returns HIGH_UV_INDEX

    val alerts = gatherWeatherAlerts()

    expectThat(alerts).contains(HIGH_UV_INDEX)
  }

  @Test
  fun `given air quality fetch fails, when alerts gathered, then weather alert still returned`() =
    runTest {

      coEvery { getAirQuality(WARSAW.toCoordinates()) } returns
        failure(IllegalStateException("offline"))

      val alerts = gatherWeatherAlerts()

      expectThat(alerts).contains(THUNDERSTORM)
    }

  @Test
  fun `given no current location, when alerts gathered, then empty list returned`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    val alerts = gatherWeatherAlerts()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given weather fetch fails, when alerts gathered, then empty list returned`() = runTest {

    every { getWeather(WARSAW.toCoordinates()) } returns
      flowOf(failure(IllegalStateException("offline")))

    val alerts = gatherWeatherAlerts()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given alert already notified, when alerts gathered again, then empty list returned`() =
    runTest {

      gatherWeatherAlerts()

      val secondCall = gatherWeatherAlerts()

      expectThat(secondCall).isEmpty()
    }
}
