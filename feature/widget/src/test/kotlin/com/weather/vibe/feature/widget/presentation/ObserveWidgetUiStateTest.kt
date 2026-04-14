package com.weather.vibe.feature.widget.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.widget.usecase.ObserveWidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetNoLocationUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetResources
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.SNAPSHOT
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import kotlin.Result.Companion.success

class ObserveWidgetUiStateTest {

  private val getRecentLocations = mockk<GetRecentLocations>()
  private val observeWidgetSnapshot = mockk<ObserveWidgetSnapshot>()
  private val resources = mockk<WidgetResources>(relaxed = true)
  private val stateFactory = WidgetStateFactory(resources = resources)
  private val observe = ObserveWidgetUiState(
    getRecentLocations = getRecentLocations,
    observeWidgetSnapshot = observeWidgetSnapshot,
    stateFactory = stateFactory
  )

  @Before
  fun setUp() {
    every { resources.temperature(any()) } answers { "${firstArg<Int>()}°" }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given no recent locations, when observed, then emits no location`() = runTest {

    every { getRecentLocations() } returns flowOf(success(emptyList()))

    observe().test {
      expectThat(awaitItem()).isA<WidgetNoLocationUiState>()
      awaitComplete()
    }
  }

  @Test
  fun `given recent location without snapshot, when observed, then emits waiting`() = runTest {

    every { getRecentLocations() } returns flowOf(success(listOf(WARSAW)))
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(null)

    observe().test {
      expectThat(awaitItem()).isA<WidgetWaitingUiState>()
      awaitComplete()
    }
  }

  @Test
  fun `given recent location with snapshot, when observed, then emits ready with location id`() = runTest {

    every { getRecentLocations() } returns flowOf(success(listOf(WARSAW)))
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(SNAPSHOT)

    observe().test {
      val state = awaitItem()
      expectThat(state).isA<WidgetReadyUiState>()
        .get { locationId }.isEqualTo(SNAPSHOT.location.id)
      awaitComplete()
    }
  }

  @Test
  fun `given snapshot emission changes, when observed, then downstream updates`() = runTest {

    every { getRecentLocations() } returns flowOf(success(listOf(WARSAW)))
    val snapshots = MutableStateFlow(SNAPSHOT)
    every { observeWidgetSnapshot(WARSAW.id) } returns snapshots

    observe().test {
      expectThat(awaitItem()).isA<WidgetReadyUiState>()
      snapshots.value = SNAPSHOT.copy(currentTemperature = 25.0)
      val updated = awaitItem()
      expectThat(updated).isA<WidgetReadyUiState>()
        .get { temperature }.isEqualTo("25°")
    }
  }

  @Test
  fun `given recent locations fail, when observed, then emits no location`() = runTest {

    every { getRecentLocations() } returns flowOf(Result.failure(RuntimeException("boom")))

    observe().test {
      expectThat(awaitItem()).isA<WidgetNoLocationUiState>()
      awaitComplete()
    }
  }

  @Test
  fun `given recents list has several, when observed, then takes the first one`() = runTest {

    val second = Location(
      id = 99L,
      name = "Kraków",
      admin1 = null,
      country = "PL",
      latitude = 1.0,
      longitude = 2.0
    )
    every { getRecentLocations() } returns flowOf(success(listOf(WARSAW, second)))
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(SNAPSHOT)

    observe().test {
      expectThat(awaitItem()).isA<WidgetReadyUiState>()
        .get { locationId }.isEqualTo(WARSAW.id)
      awaitComplete()
    }
  }
}
