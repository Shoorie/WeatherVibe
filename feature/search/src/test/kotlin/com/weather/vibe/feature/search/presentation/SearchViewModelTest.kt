package com.weather.vibe.feature.search.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy
import com.weather.vibe.domain.location.usecase.AddLocationFavoriteWithWeather
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.location.usecase.ObserveLocationFavorites
import com.weather.vibe.domain.location.usecase.ObtainCurrentLocation
import com.weather.vibe.domain.location.usecase.RemoveLocationFavorite
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.HeartClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchAction.Retry
import com.weather.vibe.feature.search.presentation.SearchAction.SetMode
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.ui.SearchResources
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.location.fixture.LocationFavoriteFixtures
import com.weather.vibe.testing.location.fixture.LocationFixtures.KRAKOW
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.first
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import java.io.IOException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val addFavorite = mockk<AddLocationFavoriteWithWeather>()
  private val getRecentLocations = mockk<GetRecentLocations>()
  private val observeFavorites = mockk<ObserveLocationFavorites>()
  private val obtainCurrentLocation = mockk<ObtainCurrentLocation>()
  private val removeFavorite = mockk<RemoveLocationFavorite>()
  private val saveRecentLocation = mockk<SaveRecentLocation>()
  private val searchLocation = mockk<SearchLocation>()
  private val resources = mockk<SearchResources>()
  private val stateFactory = SearchStateFactory(subtitle = LocationSubtitleFormatter())

  @Before
  fun setUp() {
    every { resources.defaultError() } returns DEFAULT_ERROR_MESSAGE
    mockRecentsReturn(locations = emptyList())
    mockFavoritesReturn(favorites = emptyList())
    coJustRun { saveRecentLocation(any()) }
    coJustRun { addFavorite(any(), any()) }
    coJustRun { removeFavorite(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when recent locations loaded, then state is recents`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW, KRAKOW))

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Recents>()
  }

  @Test
  fun `when recent locations loaded, then one item per location`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW, KRAKOW))

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Recents>()
      .get { locations }.hasSize(2)
  }

  @Test
  fun `given favorite location exists, when recents loaded, then matching item is favorite`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))
    mockFavoritesReturn(favorites = listOf(LocationFavoriteFixtures.WARSAW_FAVORITE))

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Recents>()
      .get { locations }.first().get { isFavorite }.isTrue()
  }

  @Test
  fun `given no recent locations, when loaded, then state is idle`() = runTest {

    mockRecentsReturn(locations = emptyList())

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Idle>()
  }

  @Test
  fun `given recent locations fetch fails, when loaded, then state is error`() = runTest {

    mockRecentsFail(IOException("boom"))

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Error>()
  }

  @Test
  fun `given recent locations fetch fails, when loaded, then error message is default`() = runTest {

    mockRecentsFail(IOException("boom"))

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Error>()
      .get { message }.isEqualTo(DEFAULT_ERROR_MESSAGE)
  }

  @Test
  fun `given query entered, when debounce elapses, then state is results`() = runTest {

    coEvery { searchLocation("krak") } returns flowOf(success(listOf(KRAKOW)))

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("krak"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    expectThat(viewModel.state.value).isA<Results>()
  }

  @Test
  fun `given query cleared to empty, when dispatched, then recents reloaded`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))
    coEvery { searchLocation("krak") } returns flowOf(success(listOf(KRAKOW)))

    val viewModel = createViewModel()

    viewModel.dispatch(QueryChange("krak"))
    advanceTimeBy(DEBOUNCE_PLUS_SLACK)
    runCurrent()

    viewModel.dispatch(QueryChange(""))

    expectThat(viewModel.state.value).isA<Recents>()
  }

  @Test
  fun `given search returns empty, when debounce elapses, then state is empty`() = runTest {

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
  fun `given picker mode, when location selected, then recent location saved`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(SetMode(SearchMode.Picker))

    viewModel.dispatch(LocationSelect(id = WARSAW.id))

    coVerify { saveRecentLocation(WARSAW) }
  }

  @Test
  fun `given picker mode, when location selected, then navigate back with result emitted`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(SetMode(SearchMode.Picker))

    viewModel.event.test {
      viewModel.dispatch(LocationSelect(id = WARSAW.id))

      val event = awaitItem()
      expectThat(event).isA<NavigateBackWithResult>()
        .get { location }.isEqualTo(WARSAW)
    }
  }

  @Test
  fun `given favorites mode, when location selected, then recent location not saved`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(SetMode(SearchMode.Favorites))

    viewModel.dispatch(LocationSelect(id = WARSAW.id))

    coVerify(exactly = 0) { saveRecentLocation(any()) }
  }

  @Test
  fun `given favorites mode and not favorite, when location selected, then favorite added`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))

    val viewModel = createViewModel()
    viewModel.dispatch(SetMode(SearchMode.Favorites))

    viewModel.dispatch(LocationSelect(id = WARSAW.id))

    coVerify { addFavorite(location = WARSAW, label = null) }
  }

  @Test
  fun `given not favorite, when heart clicked, then favorite added`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))

    val viewModel = createViewModel()

    viewModel.dispatch(HeartClick(id = WARSAW.id))

    coVerify { addFavorite(location = WARSAW, label = null) }
  }

  @Test
  fun `given already favorite, when heart clicked, then favorite removed`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))
    mockFavoritesReturn(favorites = listOf(LocationFavoriteFixtures.WARSAW_FAVORITE))

    val viewModel = createViewModel()

    viewModel.dispatch(HeartClick(id = WARSAW.id))

    coVerify { removeFavorite(id = LocationFavoriteFixtures.WARSAW_FAVORITE.id) }
  }

  @Test
  fun `given favorites at cap and item not favorite, when recents loaded, then cannot toggle`() = runTest {

    mockRecentsReturn(locations = listOf(WARSAW))
    mockFavoritesReturn(favorites = capacityFullFavorites())

    val viewModel = createViewModel()

    expectThat(viewModel.state.value).isA<Recents>()
      .get { locations }.first().get { canToggleFavorite }.isFalse()
  }

  @Test
  fun `when back clicked, then navigate back emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(BackClick)

      expectThat(awaitItem()).isA<NavigateBack>()
    }
  }

  @Test
  fun `given error with query, when retry clicked, then search retried`() = runTest {

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
  fun `given error with no query, when retry clicked, then recents reloaded`() = runTest {

    every { getRecentLocations() } returnsMany listOf(
      flowOf(failure(IOException("boom"))),
      flowOf(success(listOf(WARSAW)))
    )

    val viewModel = createViewModel()

    viewModel.dispatch(Retry)

    expectThat(viewModel.state.value).isA<Recents>()
  }

  private fun capacityFullFavorites(): List<LocationFavorite> =
    (1..LocationFavoritesPolicy.MAX_FAVORITES).map { offset ->
      LocationFavoriteFixtures.favorite(
        id = offset.toLong(),
        location = WARSAW.copy(id = FAVORITES_LOCATION_OFFSET + offset)
      )
    }

  private fun createViewModel(): SearchViewModel =
    SearchViewModel(
      resources = resources,
      stateFactory = stateFactory,
      useCases = SearchUseCases(
        addFavorite = addFavorite,
        getRecentLocations = getRecentLocations,
        observeFavorites = observeFavorites,
        obtainCurrentLocation = obtainCurrentLocation,
        removeFavorite = removeFavorite,
        saveRecentLocation = saveRecentLocation,
        searchLocation = searchLocation
      )
    )

  private fun mockRecentsReturn(locations: List<Location>) {
    every { getRecentLocations() } returns flowOf(success(locations))
  }

  private fun mockRecentsFail(error: Throwable) {
    every { getRecentLocations() } returns flowOf(failure(error))
  }

  private fun mockFavoritesReturn(favorites: List<LocationFavorite>) {
    every { observeFavorites() } returns flowOf(success(favorites))
  }

  private companion object {
    const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
    const val DEBOUNCE_PLUS_SLACK = 500L
    const val FAVORITES_LOCATION_OFFSET = 100L
  }
}
