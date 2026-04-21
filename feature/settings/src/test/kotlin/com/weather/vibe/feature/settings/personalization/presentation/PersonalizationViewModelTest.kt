package com.weather.vibe.feature.settings.personalization.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.usecase.GetAvailableBriefTones
import com.weather.vibe.domain.settings.usecase.IncludeGenre
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SelectBriefTone
import com.weather.vibe.domain.settings.usecase.ToggleTemperatureUnit
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BackClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BriefToneSelect
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.GenreRemove
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.fake.fakePersonalizationResources
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.GENRE_JAZZ
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import kotlin.Result.Companion.failure

@OptIn(ExperimentalCoroutinesApi::class)
class PersonalizationViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val getAvailableBriefTones = mockk<GetAvailableBriefTones>()
  private val includeGenre = mockk<IncludeGenre>()
  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val selectBriefTone = mockk<SelectBriefTone>()
  private val toggleTemperatureUnit = mockk<ToggleTemperatureUnit>()
  private val stateFactory = PersonalizationStateFactory(resources = fakePersonalizationResources())
  private val useCases = PersonalizationUseCases(
    getAvailableBriefTones = getAvailableBriefTones,
    includeGenre = includeGenre,
    observeUserSettings = observeUserSettings,
    selectBriefTone = selectBriefTone,
    toggleTemperatureUnit = toggleTemperatureUnit
  )

  @Before
  fun setUp() {
    every { getAvailableBriefTones() } returns PersonalizationFixtures.AVAILABLE_TONES
    every { observeUserSettings() } returns emptyFlow()
    coJustRun { selectBriefTone(any()) }
    coJustRun { includeGenre(any()) }
    coJustRun { toggleTemperatureUnit() }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when settings emitted, then state loaded with selected tone`() = runTest {

    every { observeUserSettings() } returns flowOf(
      Result.success(userSettings(briefTone = WITTY_AND_FRIENDLY))
    )

    val viewModel = createViewModel()
    runCurrent()

    val loaded = viewModel.state.value as Loaded
    val selected = loaded.briefToneOptions.single { it.isSelected }
    expectThat(selected.tone).isEqualTo(WITTY_AND_FRIENDLY)
  }

  @Test
  fun `when settings emit fails, then state switched to error`() = runTest {

    every { observeUserSettings() } returns flowOf(failure(RuntimeException("boom")))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value).isA<Error>()
  }

  @Test
  fun `when brief tone selected, then use case invoked`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(BriefToneSelect(tone = FORMAL))
    runCurrent()

    coVerify { selectBriefTone(FORMAL) }
  }

  @Test
  fun `when temperature toggled, then use case invoked`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(TemperatureUnitToggle)
    runCurrent()

    coVerify { toggleTemperatureUnit() }
  }

  @Test
  fun `when genre removed, then include genre invoked`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(GenreRemove(genre = GENRE_JAZZ))
    runCurrent()

    coVerify { includeGenre(GENRE_JAZZ) }
  }

  @Test
  fun `when back clicked, then navigate back event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(BackClick)

      expectThat(awaitItem()).isA<NavigateBack>()
    }
  }

  private fun createViewModel(): PersonalizationViewModel =
    PersonalizationViewModel(
      stateFactory = stateFactory,
      useCases = useCases
    )
}
