package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.AreAlertsEnabled
import com.weather.vibe.domain.weather.usecase.GetWeather
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
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class GatherWeatherAlertsTest {

  private val areAlertsEnabled = mockk<AreAlertsEnabled>()
  private val deduplicator = AlertDeduplicator()
  private val detectWeatherAlerts = mockk<DetectWeatherAlerts>()
  private val getWeather = mockk<GetWeather>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val gather = GatherWeatherAlerts(
    alertDeduplicator = deduplicator,
    areAlertsEnabled = areAlertsEnabled,
    detectWeatherAlerts = detectWeatherAlerts,
    getWeather = getWeather,
    observeCurrentLocation = observeCurrentLocation
  )

  @Before
  fun setUp() {
    coEvery { areAlertsEnabled() } returns true
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { getWeather(WARSAW.toCoordinates()) } returns flowOf(success(WEATHER))
    every { detectWeatherAlerts(WEATHER) } returns listOf(THUNDERSTORM)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when invoked, then alerts from detector returned`() = runTest {

    val alerts = gather()

    expectThat(alerts).hasSize(1)
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
