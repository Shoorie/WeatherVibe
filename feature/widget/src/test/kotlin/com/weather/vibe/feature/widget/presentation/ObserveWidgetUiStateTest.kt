package com.weather.vibe.feature.widget.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.widget.usecase.GetPinnedWidget
import com.weather.vibe.domain.widget.usecase.ObserveWidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetNotConfiguredUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetResources
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.SNAPSHOT
import io.mockk.coEvery
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

  private val getPinnedWidget = mockk<GetPinnedWidget>()
  private val observeWidgetSnapshot = mockk<ObserveWidgetSnapshot>()
  private val resources = mockk<WidgetResources>(relaxed = true)
  private val stateFactory = WidgetStateFactory(resources = resources)
  private val observe = ObserveWidgetUiState(
    getPinnedWidget = getPinnedWidget,
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
  fun `given no pinned location, when observed, then emits not configured`() = runTest {

    coEvery { getPinnedWidget(GLANCE_ID) } returns null

    observe(GLANCE_ID).test {
      expectThat(awaitItem()).isA<WidgetNotConfiguredUiState>()
      awaitComplete()
    }
  }

  @Test
  fun `given pinned location without snapshot, when observed, then emits waiting`() = runTest {

    coEvery { getPinnedWidget(GLANCE_ID) } returns WARSAW
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(null)

    observe(GLANCE_ID).test {
      val state = awaitItem()
      expectThat(state).isA<WidgetWaitingUiState>()
      awaitComplete()
    }
  }

  @Test
  fun `given pinned location with snapshot, when observed, then emits ready with location id`() = runTest {

    coEvery { getPinnedWidget(GLANCE_ID) } returns WARSAW
    every { observeWidgetSnapshot(WARSAW.id) } returns flowOf(SNAPSHOT)

    observe(GLANCE_ID).test {
      val state = awaitItem()
      expectThat(state).isA<WidgetReadyUiState>()
        .get { locationId }.isEqualTo(SNAPSHOT.location.id)
      awaitComplete()
    }
  }

  @Test
  fun `given snapshot emission changes, when observed, then downstream updates`() = runTest {

    coEvery { getPinnedWidget(GLANCE_ID) } returns WARSAW
    val snapshots = MutableStateFlow(SNAPSHOT)
    every { observeWidgetSnapshot(WARSAW.id) } returns snapshots

    observe(GLANCE_ID).test {
      expectThat(awaitItem()).isA<WidgetReadyUiState>()
      snapshots.value = SNAPSHOT.copy(currentTemperature = 25.0)
      val updated = awaitItem()
      expectThat(updated).isA<WidgetReadyUiState>()
        .get { temperature }.isEqualTo("25°")
    }
  }

  private companion object {
    const val GLANCE_ID = "glance-id-1"
  }
}
