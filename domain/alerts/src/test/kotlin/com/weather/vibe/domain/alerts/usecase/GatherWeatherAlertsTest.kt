package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.airquality.usecase.GetPollen
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.AreAlertsEnabled
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.POOR
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.CALM
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.HIGH_BIRCH
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.HIGH_POLLEN
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
import strikt.assertions.isEmpty
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class GatherWeatherAlertsTest {

  private val areAlertsEnabled = mockk<AreAlertsEnabled>()
  private val deduplicator = AlertDeduplicator()
  private val detectAqiAlert = mockk<DetectAqiAlert>()
  private val detectPollenAlert = mockk<DetectPollenAlert>()
  private val detectWeatherAlerts = mockk<DetectWeatherAlerts>()
  private val getAirQuality = mockk<GetAirQuality>()
  private val getPollen = mockk<GetPollen>()
  private val getWeather = mockk<GetWeather>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val detectors = AlertDetectors(
    detectAqiAlert = detectAqiAlert,
    detectPollenAlert = detectPollenAlert,
    detectWeatherAlerts = detectWeatherAlerts
  )
  private val sources = AlertSources(
    getAirQuality = getAirQuality,
    getPollen = getPollen,
    getWeather = getWeather,
    observeCurrentLocation = observeCurrentLocation
  )
  private val gather = GatherWeatherAlerts(
    alertDeduplicator = deduplicator,
    areAlertsEnabled = areAlertsEnabled,
    detectors = detectors,
    sources = sources
  )

  @Before
  fun setUp() {
    coEvery { areAlertsEnabled() } returns true
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { getWeather(WARSAW.toCoordinates()) } returns flowOf(success(WEATHER))
    every { detectWeatherAlerts(WEATHER) } returns listOf(THUNDERSTORM)
    coEvery { getAirQuality(WARSAW.toCoordinates()) } returns success(POOR)
    coEvery { getPollen(WARSAW.toCoordinates()) } returns success(CALM)
    every { detectAqiAlert(any()) } returns null
    every { detectPollenAlert(any()) } returns null
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when alerts gathered, then detected weather alert returned`() = runTest {

    val alerts = gather()

    expectThat(alerts).contains(THUNDERSTORM)
  }

  @Test
  fun `given air quality alert detected, when alerts gathered, then air quality alert returned`() =
    runTest {

      every { detectAqiAlert(POOR) } returns POOR_AIR_QUALITY

      val alerts = gather()

      expectThat(alerts).contains(POOR_AIR_QUALITY)
    }

  @Test
  fun `given pollen alert detected, when alerts gathered, then pollen alert returned`() =
    runTest {

      coEvery { getPollen(WARSAW.toCoordinates()) } returns success(HIGH_BIRCH)
      every { detectPollenAlert(HIGH_BIRCH) } returns HIGH_POLLEN

      val alerts = gather()

      expectThat(alerts).contains(HIGH_POLLEN)
    }

  @Test
  fun `given air quality fetch fails, when alerts gathered, then weather alert still returned`() =
    runTest {

      coEvery { getAirQuality(WARSAW.toCoordinates()) } returns
        failure(IllegalStateException("offline"))

      val alerts = gather()

      expectThat(alerts).contains(THUNDERSTORM)
    }

  @Test
  fun `given pollen fetch fails, when alerts gathered, then weather alert still returned`() =
    runTest {

      coEvery { getPollen(WARSAW.toCoordinates()) } returns
        failure(IllegalStateException("offline"))

      val alerts = gather()

      expectThat(alerts).contains(THUNDERSTORM)
    }

  @Test
  fun `given alerts disabled, when alerts gathered, then empty list returned`() = runTest {

    coEvery { areAlertsEnabled() } returns false

    val alerts = gather()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given no current location, when alerts gathered, then empty list returned`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    val alerts = gather()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given weather fetch fails, when alerts gathered, then exception thrown`() = runTest {

    every { getWeather(WARSAW.toCoordinates()) } returns
      flowOf(failure(IllegalStateException("offline")))

    expectThrows<IllegalStateException> { gather() }
  }

  @Test
  fun `given alert already notified, when alerts gathered again, then empty list returned`() =
    runTest {

      gather()

      val secondCall = gather()

      expectThat(secondCall).isEmpty()
    }
}
