package com.weather.vibe.feature.locations.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.domain.location.usecase.AddFavorite
import com.weather.vibe.domain.location.usecase.CompareWeather
import com.weather.vibe.domain.location.usecase.ObserveFavoritesWithWeather
import com.weather.vibe.domain.location.usecase.RefreshFavoritesWeather
import com.weather.vibe.domain.location.usecase.RemoveFavorite
import com.weather.vibe.domain.location.usecase.RenameFavorite
import com.weather.vibe.feature.locations.presentation.LocationsAction.AddCityClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.CardClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.CloseCompare
import com.weather.vibe.feature.locations.presentation.LocationsAction.Initialize
import com.weather.vibe.feature.locations.presentation.LocationsAction.RemoveClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RenameClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsEvent.NavigateToSearch
import com.weather.vibe.feature.locations.presentation.factory.LocationCardFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationCompareFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationWeatherFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationsFactories
import com.weather.vibe.feature.locations.presentation.factory.LocationsStateFactory
import com.weather.vibe.feature.locations.presentation.fake.fakeLocationsResources
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.KRAKOW_FAVORITE_ID
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.KRAKOW_WITH_WEATHER
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.WARSAW_FAVORITE_ID
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.WARSAW_WITH_WEATHER
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LocationsViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val addFavorite = mockk<AddFavorite>()
  private val compareWeather = CompareWeather()
  private val observeFavoritesWithWeather = mockk<ObserveFavoritesWithWeather>()
  private val refreshFavoritesWeather = mockk<RefreshFavoritesWeather>()
  private val removeFavorite = mockk<RemoveFavorite>()
  private val renameFavorite = mockk<RenameFavorite>()
  private val resources = fakeLocationsResources()
  private val factories = LocationsFactories(
    card = LocationCardFactory(weatherFactory = LocationWeatherFactory()),
    compare = LocationCompareFactory(weatherFactory = LocationWeatherFactory()),
    state = LocationsStateFactory(
      cardFactory = LocationCardFactory(weatherFactory = LocationWeatherFactory()),
      resources = resources
    )
  )
  private val useCases = LocationsUseCases(
    addFavorite = addFavorite,
    compareWeather = compareWeather,
    observeFavoritesWithWeather = observeFavoritesWithWeather,
    refreshFavoritesWeather = refreshFavoritesWeather,
    removeFavorite = removeFavorite,
    renameFavorite = renameFavorite
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given initialize, when favorites load, then state is loaded with cards`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    val loaded = viewModel.state.value as Loaded

    expectThat(loaded.cards).hasSize(2)
  }

  @Test
  fun `given initialize, when favorites observation fails, then state is error`() = runTest {

    every { observeFavoritesWithWeather() } returns flowOf(Result.failure(IllegalStateException()))
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    expectThat(viewModel.state.value).isA<com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error>()
  }

  @Test
  fun `given loaded with two favorites, when toggle compare mode, then compare mode becomes true`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.dispatch(ToggleCompareMode)

    expectThat(viewModel.state.value).isA<Loaded>().get { compareMode }.isTrue()
  }

  @Test
  fun `given loaded with single favorite, when toggle compare mode, then compare mode stays false`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.dispatch(ToggleCompareMode)

    expectThat(viewModel.state.value).isA<Loaded>().get { compareMode }.isA<Boolean>().isEqualTo(false)
  }

  @Test
  fun `given compare mode, when card clicked, then selection updated`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
    }

    viewModel.dispatch(CardClick(cardId = WARSAW_FAVORITE_ID.toString()))

    expectThat(viewModel.state.value).isA<Loaded>().get { selectedIds }.hasSize(1)
  }

  @Test
  fun `given two cards selected with snapshots, then compare pair is built`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
    }

    viewModel.dispatch(CardClick(cardId = WARSAW_FAVORITE_ID.toString()))
    viewModel.dispatch(CardClick(cardId = KRAKOW_FAVORITE_ID.toString()))

    expectThat(viewModel.state.value).isA<Loaded>().get { comparePair }.isNotNull()
  }

  @Test
  fun `given two cards selected but one missing snapshot, then compare pair stays null`() = runTest {

    val warsawNoSnapshot = FavoriteWithWeather(favorite = FavoriteFixtures.WARSAW_FAVORITE, snapshot = null)
    mockFavorites(sources = listOf(warsawNoSnapshot, KRAKOW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
    }

    viewModel.dispatch(CardClick(cardId = WARSAW_FAVORITE_ID.toString()))
    viewModel.dispatch(CardClick(cardId = KRAKOW_FAVORITE_ID.toString()))

    expectThat(viewModel.state.value).isA<Loaded>().get { comparePair }.isNull()
  }

  @Test
  fun `given add city click, then navigate to search event emitted`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.event.test {
      viewModel.dispatch(AddCityClick)
      expectThat(awaitItem()).isA<NavigateToSearch>()
    }
  }

  @Test
  fun `given close compare, when dispatched, then selection cleared`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
      it.dispatch(CardClick(cardId = WARSAW_FAVORITE_ID.toString()))
    }

    viewModel.dispatch(CloseCompare)

    expectThat(viewModel.state.value).isA<Loaded>().get { selectedIds }.isEmpty()
  }

  @Test
  fun `when remove click dispatched, then favorite removed by id`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    coJustRun { removeFavorite(any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.dispatch(RemoveClick(cardId = WARSAW_FAVORITE_ID.toString()))

    coVerify { removeFavorite(id = WARSAW_FAVORITE_ID) }
  }

  @Test
  fun `when rename click dispatched, then favorite renamed`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    coJustRun { refreshFavoritesWeather(any()) }
    coJustRun { renameFavorite(any(), any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.dispatch(RenameClick(cardId = WARSAW_FAVORITE_ID.toString(), label = "Praca"))

    coVerify { renameFavorite(id = WARSAW_FAVORITE_ID, label = "Praca") }
  }

  private fun mockFavorites(sources: List<FavoriteWithWeather>) {
    every { observeFavoritesWithWeather() } returns flowOf(Result.success(sources))
  }

  private fun createViewModel(): LocationsViewModel =
    LocationsViewModel(
      factories = factories,
      useCases = useCases
    )
}
