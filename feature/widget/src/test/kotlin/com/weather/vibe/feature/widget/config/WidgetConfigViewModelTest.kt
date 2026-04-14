package com.weather.vibe.feature.widget.config

import app.cash.turbine.test
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.widget.usecase.PinWidgetLocation
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Cancel
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Initialize
import com.weather.vibe.feature.widget.config.WidgetConfigAction.LocationSelect
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Retry
import com.weather.vibe.feature.widget.config.WidgetConfigEvent.Finish
import com.weather.vibe.feature.widget.config.helper.GlanceIdResolver
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Empty
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Error
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Ready
import com.weather.vibe.feature.widget.config.ui.WidgetConfigResources
import com.weather.vibe.feature.widget.work.WidgetRefreshScheduler
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.location.fixture.LocationFixtures.KRAKOW
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import java.io.IOException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@OptIn(ExperimentalCoroutinesApi::class)
class WidgetConfigViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val getRecentLocations = mockk<GetRecentLocations>()
  private val glanceIdResolver = mockk<GlanceIdResolver>()
  private val pinWidgetLocation = mockk<PinWidgetLocation>()
  private val refreshScheduler = mockk<WidgetRefreshScheduler>()
  private val resources = mockk<WidgetConfigResources>()
  private val stateFactory = WidgetConfigStateFactory(resources = resources)

  @Before
  fun setUp() {
    every { resources.defaultError() } returns ERROR_MESSAGE
    every { resources.emptyHint() } returns EMPTY_HINT
    every { resources.formatSubtitle(any(), any()) } answers { "${firstArg<String?>()}, ${secondArg<String>()}" }
    coJustRun { pinWidgetLocation(any(), any()) }
    justRun { refreshScheduler.refreshNow() }
    coEvery { glanceIdResolver.resolve(APP_WIDGET_ID) } returns GLANCE_ID
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when initialized with recent locations, then state is ready with items`() = runTest {

    mockRecentsReturn(listOf(WARSAW, KRAKOW))

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))

    expectThat(viewModel.state.value).isA<Ready>()
      .get { locations }.hasSize(2)
  }

  @Test
  fun `given no recent locations, when initialized, then state is empty with hint`() = runTest {

    mockRecentsReturn(emptyList())

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))

    expectThat(viewModel.state.value).isA<Empty>()
      .get { hint }.isEqualTo(EMPTY_HINT)
  }

  @Test
  fun `given recents fetch fails, when initialized, then state is error`() = runTest {

    mockRecentsFail(IOException("down"))

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))

    expectThat(viewModel.state.value).isA<Error>()
      .get { message }.isEqualTo(ERROR_MESSAGE)
  }

  @Test
  fun `when location selected, then pin called with resolved glance id`() = runTest {

    mockRecentsReturn(listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))
    viewModel.dispatch(LocationSelect(WARSAW.id))

    coVerify { pinWidgetLocation(GLANCE_ID, WARSAW) }
  }

  @Test
  fun `when location selected, then refresh scheduler triggered`() = runTest {

    mockRecentsReturn(listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))
    viewModel.dispatch(LocationSelect(WARSAW.id))

    verify { refreshScheduler.refreshNow() }
  }

  @Test
  fun `when location selected, then finish event emitted with app widget id`() = runTest {

    mockRecentsReturn(listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.event.test {
      viewModel.dispatch(Initialize(APP_WIDGET_ID))
      viewModel.dispatch(LocationSelect(WARSAW.id))

      val event = awaitItem()
      expectThat(event).isA<Finish>()
        .get { appWidgetId }.isEqualTo(APP_WIDGET_ID)
    }
  }

  @Test
  fun `given glance id unresolved, when location selected, then state transitions to error`() = runTest {

    coEvery { glanceIdResolver.resolve(APP_WIDGET_ID) } returns null
    mockRecentsReturn(listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))
    viewModel.dispatch(LocationSelect(WARSAW.id))

    expectThat(viewModel.state.value).isA<Error>()
  }

  @Test
  fun `when cancel dispatched, then cancel event emitted`() = runTest {

    mockRecentsReturn(emptyList())

    val viewModel = createViewModel()
    viewModel.event.test {
      viewModel.dispatch(Cancel)
      expectThat(awaitItem()).isA<WidgetConfigEvent.Cancel>()
    }
  }

  @Test
  fun `given error, when retry dispatched, then recents reloaded successfully`() = runTest {

    every { getRecentLocations() } returnsMany listOf(
      flowOf(failure(IOException("boom"))),
      flowOf(success(listOf(WARSAW)))
    )

    val viewModel = createViewModel()
    viewModel.dispatch(Initialize(APP_WIDGET_ID))
    viewModel.dispatch(Retry)

    expectThat(viewModel.state.value).isA<Ready>()
      .get { locations }.hasSize(1)
  }

  private fun createViewModel(): WidgetConfigViewModel =
    WidgetConfigViewModel(
      getRecentLocations = getRecentLocations,
      glanceIdResolver = glanceIdResolver,
      pinWidgetLocation = pinWidgetLocation,
      refreshScheduler = refreshScheduler,
      resources = resources,
      stateFactory = stateFactory
    )

  private fun mockRecentsReturn(locations: List<com.weather.vibe.domain.location.model.Location>) {
    every { getRecentLocations() } returns flowOf(success(locations))
  }

  private fun mockRecentsFail(error: Throwable) {
    every { getRecentLocations() } returns flowOf(failure(error))
  }

  private companion object {
    const val APP_WIDGET_ID = 42
    const val GLANCE_ID = "glance-42"
    const val ERROR_MESSAGE = "Something went wrong"
    const val EMPTY_HINT = "Open the app"
  }
}
