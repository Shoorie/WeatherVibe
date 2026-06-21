package com.weather.vibe.feature.settings.personalization.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.premium.usecase.ObserveLockedTones
import com.weather.vibe.domain.premium.usecase.ObservePremiumStatus
import com.weather.vibe.domain.premium.usecase.UnlockToneTemporarily
import com.weather.vibe.domain.settings.usecase.GetAvailableBriefTones
import com.weather.vibe.domain.settings.usecase.IncludeGenre
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SelectBriefTone
import com.weather.vibe.domain.settings.usecase.ToggleTemperatureUnit
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BackClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BuyPremiumClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.GenreRemove
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.LockedPersonaClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.PaywallDismiss
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.PersonaSelect
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.ToneUnlockedViaAd
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.ShowPremiumUnavailable
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
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@OptIn(ExperimentalCoroutinesApi::class)
class PersonalizationViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val getAvailableBriefTones = mockk<GetAvailableBriefTones>()
  private val includeGenre = mockk<IncludeGenre>()
  private val observeLockedTones = mockk<ObserveLockedTones>()
  private val observePremiumStatus = mockk<ObservePremiumStatus>()
  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val selectBriefTone = mockk<SelectBriefTone>()
  private val toggleTemperatureUnit = mockk<ToggleTemperatureUnit>()
  private val unlockToneTemporarily = mockk<UnlockToneTemporarily>()
  private val stateFactory = PersonalizationStateFactory(resources = fakePersonalizationResources())
  private val useCases = PersonalizationUseCases(
    getAvailableBriefTones = getAvailableBriefTones,
    includeGenre = includeGenre,
    observeLockedTones = observeLockedTones,
    observePremiumStatus = observePremiumStatus,
    observeUserSettings = observeUserSettings,
    selectBriefTone = selectBriefTone,
    toggleTemperatureUnit = toggleTemperatureUnit,
    unlockToneTemporarily = unlockToneTemporarily
  )

  @Before
  fun setUp() {
    every { getAvailableBriefTones() } returns PersonalizationFixtures.AVAILABLE_TONES
    every { observeUserSettings() } returns flowOf(success(userSettings()))
    every { observePremiumStatus() } returns flowOf(success(false))
    every { observeLockedTones() } returns flowOf(success(emptySet()))
    coJustRun { selectBriefTone(any()) }
    coJustRun { includeGenre(any()) }
    coJustRun { toggleTemperatureUnit() }
    coJustRun { unlockToneTemporarily(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when settings emitted, then state loaded with selected persona`() = runTest {

    every { observeUserSettings() } returns flowOf(
      success(userSettings(briefTone = WITTY_AND_FRIENDLY))
    )

    val viewModel = createViewModel()
    runCurrent()

    val loaded = viewModel.state.value as Loaded
    val selected = loaded.personas.single { it.isSelected }
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
  fun `when persona selected, then brief tone saved`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(PersonaSelect(tone = FORMAL))
    runCurrent()

    coVerify { selectBriefTone(FORMAL) }
  }

  @Test
  fun `when locked persona clicked, then paywall opened for that tone`() = runTest {

    val viewModel = createViewModel()
    runCurrent()

    viewModel.dispatch(LockedPersonaClick(tone = COACH))
    runCurrent()

    val loaded = viewModel.state.value as Loaded
    expectThat(loaded.paywall).isNotNull().get { tone }.isEqualTo(COACH)
  }

  @Test
  fun `given paywall open, when dismissed, then paywall cleared`() = runTest {

    val viewModel = createViewModel()
    runCurrent()
    viewModel.dispatch(LockedPersonaClick(tone = COACH))
    runCurrent()

    viewModel.dispatch(PaywallDismiss)
    runCurrent()

    val loaded = viewModel.state.value as Loaded
    expectThat(loaded.paywall).isNull()
  }

  @Test
  fun `when tone unlocked via ad, then tone unlocked temporarily`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(ToneUnlockedViaAd(tone = COACH))
    runCurrent()

    coVerify { unlockToneTemporarily(COACH) }
  }

  @Test
  fun `when buy premium clicked, then premium unavailable event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(BuyPremiumClick)

      expectThat(awaitItem()).isA<ShowPremiumUnavailable>()
    }
  }

  @Test
  fun `when temperature toggled, then temperature unit saved`() = runTest {

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
