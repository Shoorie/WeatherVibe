package com.weather.vibe.feature.widget.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.widget.usecase.ObserveWidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
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

class ObserveWidgetUiStateTest {

  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val observeWidgetSnapshot = mockk<ObserveWidgetSnapshot>()
  private val resources = mockk<WidgetResources>(relaxed = true)
  private val stateFactory = WidgetStateFactory(resources = resources)
  private val observe = ObserveWidgetUiState(
    observeCurrentLocation = observeCurrentLocation,
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
  fun `given no current location, when observed, then emits no location`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    observe().test {
      expectThat(awaitItem()).isA<WidgetUiState.NoLocation>()
      awaitComplete()
    }
  }

  @Test
  fun `given current location without snapshot, when observed, then emits waiting`() = runTest {

    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(null)

    observe().test {
      expectThat(awaitItem()).isA<WidgetUiState.Waiting>()
      awaitComplete()
    }
  }

  @Test
  fun `given current location with snapshot, when observed, then emits weather with location id`() = runTest {

    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(SNAPSHOT)

    observe().test {
      val state = awaitItem()
      expectThat(state).isA<WidgetUiState.Weather>()
        .get { locationId }.isEqualTo(SNAPSHOT.location.id)
      awaitComplete()
    }
  }

  @Test
  fun `given snapshot emission changes, when observed, then downstream updates`() = runTest {

    every { observeCurrentLocation() } returns flowOf(WARSAW)
    val snapshots = MutableStateFlow(SNAPSHOT)
    every { observeWidgetSnapshot(WARSAW.id) } returns snapshots

    observe().test {
      expectThat(awaitItem()).isA<WidgetUiState.Weather>()
      snapshots.value = SNAPSHOT.copy(currentTemperature = 25.0)
      val updated = awaitItem()
      expectThat(updated).isA<WidgetUiState.Weather>()
        .get { temperature }.isEqualTo("25°")
    }
  }

  @Test
  fun `given current location fails, when observed, then emits error`() = runTest {

    every { observeCurrentLocation() } returns kotlinx.coroutines.flow.flow {
      throw RuntimeException("boom")
    }

    observe().test {
      expectThat(awaitItem()).isA<WidgetUiState.Error>()
      awaitComplete()
    }
  }

  @Test
  fun `given current location switches, when observed, then downstream re-evaluates`() = runTest {

    val second = Location(
      id = 99L,
      name = "Kraków",
      admin1 = null,
      country = "PL",
      latitude = 1.0,
      longitude = 2.0
    )
    val currentLocation = MutableStateFlow<Location?>(WARSAW)
    every { observeCurrentLocation() } returns currentLocation
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(SNAPSHOT)
    every { observeWidgetSnapshot(second.id) } returns flowOf(null)

    observe().test {
      expectThat(awaitItem()).isA<WidgetUiState.Weather>()
        .get { locationId }.isEqualTo(WARSAW.id)
      currentLocation.value = second
      expectThat(awaitItem()).isA<WidgetUiState.Waiting>()
    }
  }
}
