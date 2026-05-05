package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.usecase.GetPollen
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.CALM
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.HIGH_BIRCH
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.HIGH_POLLEN
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
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

class GatherPollenAlertsTest {

  private val deduplicator = AlertDeduplicator()
  private val detectPollenAlert = mockk<DetectPollenAlert>()
  private val getPollen = mockk<GetPollen>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val gather = GatherPollenAlerts(
    alertDeduplicator = deduplicator,
    detectPollenAlert = detectPollenAlert,
    getPollen = getPollen,
    observeCurrentLocation = observeCurrentLocation
  )

  @Before
  fun setUp() {
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    coEvery { getPollen(WARSAW.toCoordinates()) } returns success(HIGH_BIRCH)
    every { detectPollenAlert(HIGH_BIRCH) } returns HIGH_POLLEN
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when high pollen detected, then pollen alert returned`() = runTest {

    val alerts = gather()

    expectThat(alerts).contains(HIGH_POLLEN)
  }

  @Test
  fun `given pollen below threshold, when gathered, then empty list returned`() = runTest {

    coEvery { getPollen(WARSAW.toCoordinates()) } returns success(CALM)
    every { detectPollenAlert(CALM) } returns null

    val alerts = gather()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given pollen fetch fails, when gathered, then empty list returned`() = runTest {

    coEvery { getPollen(WARSAW.toCoordinates()) } returns
      failure(IllegalStateException("offline"))

    val alerts = gather()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given no current location, when gathered, then empty list returned`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    val alerts = gather()

    expectThat(alerts).isEmpty()
  }

  @Test
  fun `given alert already notified, when gathered again, then empty list returned`() = runTest {

    gather()

    val secondCall = gather()

    expectThat(secondCall).isEmpty()
  }
}
