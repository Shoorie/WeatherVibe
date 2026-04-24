package com.weather.vibe.feature.locations.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.location.usecase.CompareLocationWeather
import com.weather.vibe.domain.location.usecase.ObserveLocationFavoritesWithWeather
import com.weather.vibe.domain.location.usecase.RefreshLocationFavoritesWeather
import com.weather.vibe.domain.location.usecase.RefreshOutdatedLocationFavoritesWeather
import com.weather.vibe.domain.location.usecase.RemoveLocationFavorite
import com.weather.vibe.domain.location.usecase.RenameLocationFavorite
import com.weather.vibe.domain.location.usecase.ReorderLocationFavorites as ReorderLocationFavoritesUseCase
import com.weather.vibe.domain.location.usecase.RestoreLocationFavoriteAtOriginalPosition
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.usecase.ObserveTemperatureUnit
import com.weather.vibe.feature.locations.presentation.LocationsAction.AddLocationClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ExitCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsAction.Initialize
import com.weather.vibe.feature.locations.presentation.LocationsAction.OpenLocationDetails
import com.weather.vibe.feature.locations.presentation.LocationsAction.RemoveLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RenameLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ReorderLocationFavorites
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsEvent.NavigateToSearch
import com.weather.vibe.feature.locations.presentation.factory.LocationCardFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationCompareFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationComparePairBuilder
import com.weather.vibe.feature.locations.presentation.factory.LocationWeatherFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationsFactories
import com.weather.vibe.feature.locations.presentation.factory.LocationsLoadedFactory
import com.weather.vibe.feature.locations.presentation.factory.LocationsStateFactory
import com.weather.vibe.feature.locations.presentation.factory.TemperatureAxisFactory
import com.weather.vibe.feature.locations.presentation.fake.fakeLocationsResources
import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.KRAKOW_FAVORITE_ID
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.KRAKOW_WITH_WEATHER
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE_ID
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_WITH_WEATHER
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.testing.coroutines.MainDispatcherRule
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
import kotlin.Result.Companion.failure

@OptIn(ExperimentalCoroutinesApi::class)
class LocationsViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val compareLocationWeather = CompareLocationWeather()
  private val observeFavoritesWithWeather = mockk<ObserveLocationFavoritesWithWeather>()
  private val observeTemperatureUnit = mockk<ObserveTemperatureUnit>()
  private val refreshFavoritesWeather = mockk<RefreshLocationFavoritesWeather>()
  private val refreshOutdatedFavoritesWeather = mockk<RefreshOutdatedLocationFavoritesWeather>(relaxed = true)
  private val removeFavorite = mockk<RemoveLocationFavorite>()
  private val renameFavorite = mockk<RenameLocationFavorite>()
  private val reorderFavorites = mockk<ReorderLocationFavoritesUseCase>()
  private val restoreFavoriteAtOriginalPosition =
    mockk<RestoreLocationFavoriteAtOriginalPosition>()
  private val resources = fakeLocationsResources()
  private val temperatureFormatter = fakeTemperatureFormatter()
  private val cardFactory = LocationCardFactory(
    temperatureFormatter = temperatureFormatter,
    weatherFactory = LocationWeatherFactory()
  )
  private val compareFactory = LocationCompareFactory(
    temperatureFormatter = temperatureFormatter,
    weatherFactory = LocationWeatherFactory()
  )
  private val stateFactory = LocationsStateFactory(
    cardFactory = cardFactory,
    resources = resources
  )
  private val loadedFactory = LocationsLoadedFactory(
    stateFactory = stateFactory,
    comparePairBuilder = LocationComparePairBuilder(
      compareFactory = compareFactory,
      compareLocationWeather = compareLocationWeather,
      temperatureAxisFactory = TemperatureAxisFactory(
        temperature = temperatureFormatter
      )
    )
  )
  private val factories = LocationsFactories(
    card = cardFactory,
    compare = compareFactory,
    loaded = loadedFactory,
    state = stateFactory
  )
  private val useCases = LocationsUseCases(
    compareLocationWeather = compareLocationWeather,
    observeFavoritesWithWeather = observeFavoritesWithWeather,
    observeTemperatureUnit = observeTemperatureUnit,
    refreshFavoritesWeather = refreshFavoritesWeather,
    refreshOutdatedFavoritesWeather = refreshOutdatedFavoritesWeather,
    removeFavorite = removeFavorite,
    renameFavorite = renameFavorite,
    reorderFavorites = reorderFavorites,
    restoreFavoriteAtOriginalPosition = restoreFavoriteAtOriginalPosition
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given initialize, when favorites load, then state is loaded with cards`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    val loaded = viewModel.state.value as Loaded

    expectThat(loaded.cards).hasSize(2)
  }

  @Test
  fun `when initialized, then outdated favorites weather refreshed`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    createViewModel().also { it.dispatch(Initialize) }

    coVerify { refreshOutdatedFavoritesWeather() }
  }

  @Test
  fun `given initialize, when favorites observation fails, then state is error`() = runTest {

    every { observeFavoritesWithWeather() } returns flowOf(failure(IllegalStateException()))
    every { observeTemperatureUnit() } returns flowOf(CELSIUS)
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    expectThat(viewModel.state.value).isA<Error>()
  }

  @Test
  fun `given loaded with two favorites, when toggle compare mode, then compare mode becomes true`() =
    runTest {

      mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
      val viewModel = createViewModel().also { it.dispatch(Initialize) }

      viewModel.dispatch(ToggleCompareMode)

      expectThat(viewModel.state.value)
        .isA<Loaded>().get { compareMode }
        .isTrue()
    }

  @Test
  fun `given loaded with single favorite, when toggle compare mode, then compare mode stays false`() =
    runTest {

      mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
      val viewModel = createViewModel().also { it.dispatch(Initialize) }

      viewModel.dispatch(ToggleCompareMode)

      expectThat(viewModel.state.value)
        .isA<Loaded>().get { compareMode }
        .isA<Boolean>()
        .isEqualTo(false)
    }

  @Test
  fun `given compare mode, when card clicked, then selection updated`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
    }

    viewModel.dispatch(OpenLocationDetails(favoriteId = WARSAW_FAVORITE_ID))

    expectThat(viewModel.state.value)
      .isA<Loaded>()
      .get { selectedIds }
      .hasSize(1)
  }

  @Test
  fun `given two cards selected with snapshots, then compare pair is built`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
    }

    viewModel.dispatch(OpenLocationDetails(favoriteId = WARSAW_FAVORITE_ID))
    viewModel.dispatch(OpenLocationDetails(favoriteId = KRAKOW_FAVORITE_ID))

    expectThat(viewModel.state.value)
      .isA<Loaded>()
      .get { comparePair }
      .isNotNull()
  }

  @Test
  fun `given two cards selected but one missing snapshot, then compare pair stays null`() =
    runTest {

      val noSnapshot = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null)
      mockFavorites(sources = listOf(noSnapshot, KRAKOW_WITH_WEATHER))
      val viewModel = createViewModel().also {
        it.dispatch(Initialize)
        it.dispatch(ToggleCompareMode)
      }

      viewModel.dispatch(OpenLocationDetails(favoriteId = WARSAW_FAVORITE_ID))
      viewModel.dispatch(OpenLocationDetails(favoriteId = KRAKOW_FAVORITE_ID))

      expectThat(viewModel.state.value)
        .isA<Loaded>()
        .get { comparePair }
        .isNull()
    }

  @Test
  fun `given add city click, then navigate to search event emitted`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.event.test {
      viewModel.dispatch(AddLocationClick)
      expectThat(awaitItem()).isA<NavigateToSearch>()
    }
  }

  @Test
  fun `given close compare, when dispatched, then selection cleared`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
    val viewModel = createViewModel().also {
      it.dispatch(Initialize)
      it.dispatch(ToggleCompareMode)
      it.dispatch(OpenLocationDetails(favoriteId = WARSAW_FAVORITE_ID))
    }

    viewModel.dispatch(ExitCompareMode)

    expectThat(viewModel.state.value)
      .isA<Loaded>()
      .get { selectedIds }
      .isEmpty()
  }

  @Test
  fun `when remove click dispatched, then favorite removed by id`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    coJustRun { removeFavorite(any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.dispatch(RemoveLocationFavoriteClick(favoriteId = WARSAW_FAVORITE_ID))

    coVerify { removeFavorite(id = WARSAW_FAVORITE_ID) }
  }

  @Test
  fun `when location favorites reordered, then reorder use case called with ordered ids`() =
    runTest {

      mockFavorites(sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER))
      coJustRun { reorderFavorites(any()) }
      val viewModel = createViewModel().also { it.dispatch(Initialize) }
      val orderedIds = listOf(KRAKOW_FAVORITE_ID, WARSAW_FAVORITE_ID)

      viewModel.dispatch(ReorderLocationFavorites(orderedIds = orderedIds))

      coVerify { reorderFavorites(orderedIds = orderedIds) }
    }

  @Test
  fun `when rename click dispatched, then favorite renamed`() = runTest {

    mockFavorites(sources = listOf(WARSAW_WITH_WEATHER))
    coJustRun { renameFavorite(any(), any()) }
    val viewModel = createViewModel().also { it.dispatch(Initialize) }

    viewModel.dispatch(
      RenameLocationFavoriteClick(
        favoriteId = WARSAW_FAVORITE_ID,
        label = "Praca"
      )
    )

    coVerify { renameFavorite(id = WARSAW_FAVORITE_ID, label = "Praca") }
  }

  private fun mockFavorites(sources: List<LocationFavoriteWithWeather>) {
    every { observeFavoritesWithWeather() } returns flowOf(Result.success(sources))
    every { observeTemperatureUnit() } returns flowOf(CELSIUS)
  }

  private fun createViewModel(): LocationsViewModel =
    LocationsViewModel(
      factories = factories,
      useCases = useCases
    )
}
