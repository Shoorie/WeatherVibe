package com.weather.vibe.feature.settings.presentation

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.GetAvailableBriefTones
import com.weather.vibe.domain.settings.usecase.IncludeGenre
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SelectBriefTone
import com.weather.vibe.domain.settings.usecase.SetMorningBriefEnabled
import com.weather.vibe.domain.settings.usecase.SetWeatherAlertsEnabled
import com.weather.vibe.domain.settings.usecase.ToggleTemperatureUnit
import com.weather.vibe.feature.settings.presentation.SettingsAction.BackClick
import com.weather.vibe.feature.settings.presentation.SettingsAction.BriefToneSelect
import com.weather.vibe.feature.settings.presentation.SettingsAction.GenreRemove
import com.weather.vibe.feature.settings.presentation.SettingsAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.presentation.SettingsEvent.NavigateBack
import com.weather.vibe.feature.settings.presentation.fake.fakeSettingsResources
import com.weather.vibe.feature.settings.presentation.fixture.SettingsResourcesFixtures.DEFAULT_ERROR
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.settings.fixture.GenreFixtures.METAL
import com.weather.vibe.testing.settings.fixture.GenreFixtures.POP
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.DEFAULT_SETTINGS
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import strikt.assertions.map

class SettingsViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule(dispatcher = StandardTestDispatcher())

  private val includeGenre = mockk<IncludeGenre>()
  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val selectBriefTone = mockk<SelectBriefTone>()
  private val setMorningBriefEnabled = mockk<SetMorningBriefEnabled>()
  private val setWeatherAlertsEnabled = mockk<SetWeatherAlertsEnabled>()
  private val toggleTemperatureUnit = mockk<ToggleTemperatureUnit>()
  private val resources = fakeSettingsResources()
  private val stateFactory = SettingsStateFactory(resources = resources)

  private lateinit var observed: MutableStateFlow<Result<UserSettings>>

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when observed settings succeed, then state transitions from loading to loaded`() = runTest {

    val viewModel = createViewModel(initial = DEFAULT_SETTINGS)

    viewModel.state.test {
      expectThat(awaitItem()).isA<Loading>()
      expectThat(awaitItem()).isA<Loaded>()
    }
  }

  @Test
  fun `given witty tone in settings, when observed, then loaded state selects witty`() = runTest {

    val viewModel = createViewModel(initial = userSettings(briefTone = WITTY_AND_FRIENDLY))

    viewModel.state.test {
      awaitLoading()
      expectThat(selectedTone(awaitLoaded())).isEqualTo(WITTY_AND_FRIENDLY)
    }
  }

  @Test
  fun `when observed settings fail, then state transitions to error with default message`() = runTest {

    val viewModel = createViewModelWith(
      flow = flowOf(Result.failure(IllegalStateException("boom")))
    )

    viewModel.state.test {
      awaitLoading()
      expectThat(awaitItem()).isA<SettingsUiState.Error>()
        .get { message }.isEqualTo(DEFAULT_ERROR)
    }
  }

  @Test
  fun `when brief tone select dispatched, then loaded state updates selected tone`() = runTest {

    val viewModel = createViewModel(initial = DEFAULT_SETTINGS)
    mockBriefTone(tone = FORMAL, emits = DEFAULT_SETTINGS.withBriefTone(FORMAL))

    viewModel.state.test {

      awaitLoading()
      expectThat(selectedTone(awaitLoaded())).isEqualTo(WITTY_AND_FRIENDLY)

      viewModel.dispatch(BriefToneSelect(tone = FORMAL))

      expectThat(selectedTone(awaitLoaded())).isEqualTo(FORMAL)
    }
  }

  @Test
  fun `when genre remove dispatched, then loaded state drops the genre chip`() = runTest {

    val viewModel = createViewModel(initial = userSettings(excludedGenres = setOf(POP, METAL)))
    mockGenreRemove(genre = POP, emits = userSettings(excludedGenres = setOf(METAL)))

    viewModel.state.test {

      awaitLoading()
      expectThat(awaitLoaded().genreChips).map { it.name }.containsExactly(METAL, POP)

      viewModel.dispatch(GenreRemove(genre = POP))

      expectThat(awaitLoaded().genreChips).map { it.name }.containsExactly(METAL)
    }
  }

  @Test
  fun `when temperature unit toggle dispatched, then loaded state flips celsius flag`() = runTest {

    val viewModel = createViewModel(initial = DEFAULT_SETTINGS)
    mockTemperatureToggle(emits = DEFAULT_SETTINGS.withToggledTemperatureUnit())

    viewModel.state.test {

      awaitLoading()
      expectThat(awaitLoaded().isCelsius).isTrue()

      viewModel.dispatch(TemperatureUnitToggle)

      expectThat(awaitLoaded().isCelsius).isFalse()
    }
  }

  @Test
  fun `when back click dispatched, then navigate back event emitted`() = runTest {

    val viewModel = createViewModel(initial = DEFAULT_SETTINGS)

    viewModel.event.test {
      viewModel.dispatch(BackClick)
      expectThat(awaitItem()).isA<NavigateBack>()
    }
  }

  @Test
  fun `given write use case throws, when dispatched, then state transitions to error`() = runTest {

    val viewModel = createViewModel(initial = DEFAULT_SETTINGS)
    coEvery { selectBriefTone(FORMAL) } throws IllegalStateException("boom")

    viewModel.state.test {

      awaitLoading()
      awaitLoaded()

      viewModel.dispatch(BriefToneSelect(tone = FORMAL))

      expectThat(awaitItem()).isA<SettingsUiState.Error>()
    }
  }

  private fun createViewModel(initial: UserSettings = DEFAULT_SETTINGS): SettingsViewModel {
    observed = MutableStateFlow(Result.success(initial))
    return createViewModelWith(flow = observed)
  }

  private fun createViewModelWith(flow: Flow<Result<UserSettings>>): SettingsViewModel {
    every { observeUserSettings() } returns flow
    val useCases = SettingsUseCases(
      getAvailableBriefTones = GetAvailableBriefTones(),
      includeGenre = includeGenre,
      observeUserSettings = observeUserSettings,
      selectBriefTone = selectBriefTone,
      setMorningBriefEnabled = setMorningBriefEnabled,
      setWeatherAlertsEnabled = setWeatherAlertsEnabled,
      toggleTemperatureUnit = toggleTemperatureUnit
    )
    return SettingsViewModel(
      resources = resources,
      stateFactory = stateFactory,
      useCases = useCases
    )
  }

  private fun mockBriefTone(tone: BriefTone, emits: UserSettings) {
    coEvery { selectBriefTone(tone) } coAnswers
      { observed.value = Result.success(emits) }
  }

  private fun mockGenreRemove(genre: String, emits: UserSettings) {
    coEvery { includeGenre(genre) } coAnswers
      { observed.value = Result.success(emits) }
  }

  private fun mockTemperatureToggle(emits: UserSettings) {
    coEvery { toggleTemperatureUnit() } coAnswers
      { observed.value = Result.success(emits) }
  }

  private suspend fun ReceiveTurbine<SettingsUiState>.awaitLoading() {
    expectThat(awaitItem()).isA<Loading>()
  }

  private suspend fun ReceiveTurbine<SettingsUiState>.awaitLoaded(): Loaded {
    val item = awaitItem()
    expectThat(item).isA<Loaded>()
    return item as Loaded
  }

  private fun selectedTone(loaded: Loaded) =
    loaded.briefToneOptions.single { it.isSelected }.tone
}
