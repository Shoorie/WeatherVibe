package com.weather.vibe.feature.search.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.domain.location.usecase.GetRecentLocationsWithTemperature
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.settings.usecase.ObserveTemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchAction.Retry
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.ui.SearchResources
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.location.fixture.LocationFixtures.KRAKOW
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.location.fixture.LocationFixtures.locationWithTemperature
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
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
class SearchViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val getRecentLocationsWithTemperature = mockk<GetRecentLocationsWithTemperature>()
  private val observeTemperatureUnit = mockk<ObserveTemperatureUnit>()
  private val saveRecentLocation = mockk<SaveRecentLocation>()
  private val searchLocation = mockk<SearchLocation>()
  private val resources = mockk<SearchResources>()
  private val temperature = mockk<TemperatureFormatter>()
  private val stateFactory = SearchStateFactory(
    subtitle = LocationSubtitleFormatter(),
    temperature = temperature
  )

  @Before
  fun setUp() {
    every { resources.defaultError() } returns DEFAULT_ERROR_MESSAGE
    every { temperature.format(celsius = any(), unit = any()) } returns FORMATTED_TEMPERATURE
    mockUnit(unit = CELSIUS)
    mockRecentsReturn(entries = emptyList())
    coJustRun { saveRecentLocation(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when recent locations loaded, then state is recents with entries`() = runTest {

    mockRecentsReturn(
      entries = listOf(
        locationWithTemperature(location = WARSAW, currentTemperature = 15.0),
        locationWithTemperature(location = KRAKOW, currentTemperature = 12.0)
      )
    )

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Recents>()
      .get { locations }.hasSize(2)
  }

  @Test
  fun `given no recent locations, when loaded, then state is idle`() = runTest {

    mockRecentsReturn(entries = emptyList())

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Idle>()
  }

  @Test
  fun `given recent locations fetch fails, when loaded, then state is error`() = runTest {

    mockRecentsFail(IOException("boom"))

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Error>()
      .get { message }.isEqualTo(DEFAULT_ERROR_MESSAGE)
  }

  @Test
  fun `given non-empty query, when debounce elapses, then search results shown`() = runTest {

    coEvery { searchLocation("krak") } returns flowOf(success(listOf(KRAKOW)))

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("krak"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    expectThat(viewModel.state.value).isA<Results>()
      .get { locations }.hasSize(1)
  }

  @Test
  fun `given query cleared to empty, when dispatched, then recent locations reloaded`() = runTest {

    mockRecentsReturn(entries = listOf(locationWithTemperature(location = WARSAW)))
    coEvery { searchLocation("krak") } returns flowOf(success(listOf(KRAKOW)))

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("krak"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    viewModel.dispatch(QueryChange(""))

    expectThat(viewModel.state.value).isA<Recents>()
  }

  @Test
  fun `given search returns empty, when debounce elapses, then state is empty with query`() = runTest {

    coEvery { searchLocation("xyz") } returns flowOf(success(emptyList()))

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("xyz"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    expectThat(viewModel.state.value).isA<Empty>()
      .get { query }.isEqualTo("xyz")
  }

  @Test
  fun `given search fails, when debounce elapses, then state is error`() = runTest {

    coEvery { searchLocation("xyz") } returns flowOf(failure(IOException("down")))

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("xyz"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    expectThat(viewModel.state.value).isA<Error>()
  }

  @Test
  fun `when location selected, then save recent location called with matching location`() = runTest {

    mockRecentsReturn(entries = listOf(locationWithTemperature(location = WARSAW)))

    val viewModel = createViewModel()

    viewModel.dispatch(LocationSelect(id = WARSAW.id))

    coVerify { saveRecentLocation(WARSAW) }
  }

  @Test
  fun `when location selected, then navigate back with result event emitted`() = runTest {

    mockRecentsReturn(entries = listOf(locationWithTemperature(location = WARSAW)))

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(LocationSelect(id = WARSAW.id))

      val event = awaitItem()
      expectThat(event).isA<NavigateBackWithResult>()
        .get { location }.isEqualTo(WARSAW)
    }
  }

  @Test
  fun `given save throws, when location selected, then state transitions to error`() = runTest {

    mockRecentsReturn(entries = listOf(locationWithTemperature(location = WARSAW)))
    coEvery { saveRecentLocation(WARSAW) } throws IllegalStateException("db down")

    val viewModel = createViewModel()

    viewModel.dispatch(LocationSelect(id = WARSAW.id))

    expectThat(viewModel.state.value).isA<Error>()
  }

  @Test
  fun `when back clicked, then navigate back event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(BackClick)

      expectThat(awaitItem()).isA<NavigateBack>()
    }
  }

  @Test
  fun `given error with non-empty query, when retry clicked, then search retried successfully`() = runTest {

    coEvery { searchLocation("kra") } returnsMany listOf(
      flowOf(failure(IOException("first"))),
      flowOf(success(listOf(KRAKOW)))
    )

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("kra"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    viewModel.dispatch(Retry)

    expectThat(viewModel.state.value).isA<Results>()
  }

  @Test
  fun `given error with empty query, when retry clicked, then recent locations reloaded`() = runTest {

    every { getRecentLocationsWithTemperature() } returnsMany listOf(
      flowOf(failure(IOException("boom"))),
      flowOf(success(listOf(locationWithTemperature(location = WARSAW))))
    )

    val viewModel = createViewModel()

    viewModel.dispatch(Retry)

    expectThat(viewModel.state.value).isA<Recents>()
  }

  @Test
  fun `given fahrenheit setting, when recents loaded, then factory called with fahrenheit`() = runTest {

    mockUnit(unit = FAHRENHEIT)
    mockRecentsReturn(
      entries = listOf(
        locationWithTemperature(
          location = WARSAW,
          currentTemperature = 20.0
        )
      )
    )

    createViewModel()

    verify { temperature.format(celsius = 20.0, unit = FAHRENHEIT) }
  }

  @Test
  fun `given unit changes after recents loaded, when unit emits, then recents rebuilt with new unit`() = runTest {

    val unitFlow = MutableStateFlow(CELSIUS)
    every { observeTemperatureUnit() } returns unitFlow
    mockRecentsReturn(
      entries = listOf(
        locationWithTemperature(
          location = WARSAW,
          currentTemperature = 20.0
        )
      )
    )

    createViewModel()

    unitFlow.value = FAHRENHEIT

    verify { temperature.format(celsius = 20.0, unit = FAHRENHEIT) }
  }

  private fun createViewModel(): SearchViewModel =
    SearchViewModel(
      resources = resources,
      stateFactory = stateFactory,
      useCases = SearchUseCases(
        getRecentLocationsWithTemperature = getRecentLocationsWithTemperature,
        observeTemperatureUnit = observeTemperatureUnit,
        saveRecentLocation = saveRecentLocation,
        searchLocation = searchLocation
      )
    )

  private fun mockUnit(unit: TemperatureUnit) {
    every { observeTemperatureUnit() } returns flowOf(unit)
  }

  private fun mockRecentsReturn(entries: List<LocationWithTemperature>) {
    every { getRecentLocationsWithTemperature() } returns
      flowOf(success(entries))
  }

  private fun mockRecentsFail(error: Throwable) {
    every { getRecentLocationsWithTemperature() } returns
      flowOf(failure(error))
  }

  private companion object {
    const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
    const val DEBOUNCE_PLUS_SLACK = 500L
    const val FORMATTED_TEMPERATURE = "15°"
  }
}
