package com.weather.vibe.feature.profile.presentation

import android.util.Log
import app.cash.turbine.test
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.usecase.ObserveThemeMode
import com.weather.vibe.domain.appearance.usecase.SetThemeMode
import com.weather.vibe.domain.location.usecase.ObserveLocationFavoritesCount
import com.weather.vibe.domain.profile.usecase.ObserveProfile
import com.weather.vibe.domain.profile.usecase.SaveUsername
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.viberating.model.VibeOverview
import com.weather.vibe.domain.viberating.usecase.ObserveVibeOverview
import com.weather.vibe.feature.profile.presentation.ProfileAction.ContactClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.LicensesClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameDismiss
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameSubmit
import com.weather.vibe.feature.profile.presentation.ProfileAction.NotificationsClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PersonalizationClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PrivacyClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.StatClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.ThemeSelect
import com.weather.vibe.feature.profile.presentation.ProfileAction.UsernameChanged
import com.weather.vibe.feature.profile.presentation.ProfileAction.VibeRowClick
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenContact
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenLicenses
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenLocations
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenNotifications
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPersonalization
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPrivacy
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenVibeHistory
import com.weather.vibe.feature.profile.presentation.fake.fakeProfileResources
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_ON
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.TONE_LABEL_FORMAL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.USERNAME_JOHN
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.greeting
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.profileSummary
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
import strikt.assertions.isFalse
import strikt.assertions.isNotNull
import strikt.assertions.isTrue
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val observeProfile = mockk<ObserveProfile>()
  private val observeFavoritesCount = mockk<ObserveLocationFavoritesCount>()
  private val observeVibeOverview = mockk<ObserveVibeOverview>()
  private val observeThemeMode = mockk<ObserveThemeMode>()
  private val setThemeMode = mockk<SetThemeMode>()
  private val saveUsername = mockk<SaveUsername>()
  private val stateFactory = ProfileStateFactory(resources = fakeProfileResources())
  private val useCases = ProfileUseCases(
    observeFavoritesCount = observeFavoritesCount,
    observeProfile = observeProfile,
    observeThemeMode = observeThemeMode,
    observeUserSettings = observeUserSettings,
    observeVibeOverview = observeVibeOverview,
    saveUsername = saveUsername,
    setThemeMode = setThemeMode
  )

  @Before
  fun setUp() {
    mockkStatic(Log::class)
    every { Log.e(any(), any(), any()) } returns 0
    every { observeProfile() } returns flowOf(profileSummary())
    every { observeUserSettings() } returns flowOf(success(userSettings()))
    every { observeFavoritesCount() } returns flowOf(success(0))
    every { observeVibeOverview() } returns flowOf(VibeOverview.EMPTY)
    every { observeThemeMode() } returns flowOf(AUTO)
    coJustRun { saveUsername(any()) }
    coJustRun { setThemeMode(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when profile emitted, then header greeting reflects user`() = runTest {

    every { observeProfile() } returns flowOf(profileSummary())

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.greeting).isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `when profile emitted, then avatar uses first letter of name`() = runTest {

    every { observeProfile() } returns flowOf(profileSummary(username = USERNAME_JOHN))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.avatarInitial).isEqualTo("J")
  }

  @Test
  fun `given formal tone, when settings emitted, then brief tone label uses formal mapping`() = runTest {

    every { observeUserSettings() } returns flowOf(success(userSettings(briefTone = FORMAL)))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.briefToneLabel).isEqualTo(TONE_LABEL_FORMAL)
  }

  @Test
  fun `given morning brief enabled, when settings emitted, then brief stat is on`() = runTest {

    every { observeUserSettings() } returns flowOf(
      success(userSettings(morningBriefEnabled = true))
    )

    val viewModel = createViewModel()
    runCurrent()

    val brief = viewModel.state.value.quickStats.first { it.type == MORNING_BRIEF }
    expectThat(brief.value).isEqualTo(STATUS_ON)
  }

  @Test
  fun `given alerts enabled, when settings emitted, then alerts stat is on`() = runTest {

    every { observeUserSettings() } returns flowOf(success(userSettings(weatherAlertsEnabled = true)))

    val viewModel = createViewModel()
    runCurrent()

    val alerts = viewModel.state.value.quickStats.first { it.type == ALERTS }
    expectThat(alerts.value).isEqualTo(STATUS_ON)
  }

  @Test
  fun `given settings failure, when snapshot received, then header greeting still updates`() = runTest {

    every { observeUserSettings() } returns flowOf(failure(RuntimeException("boom")))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.greeting).isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `when non-empty vibe overview emitted, then vibe row becomes loaded`() = runTest {

    every { observeVibeOverview() } returns flowOf(
      VibeOverview(averageRating = 4.0, streakDays = 1, totalEntries = 2)
    )

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.vibeRow).isA<Loaded>()
  }

  @Test
  fun `when theme mode emitted, then appearance row reflects mode`() = runTest {

    every { observeThemeMode() } returns flowOf(DARK)

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.appearanceRow)
      .isNotNull()
      .get { current }.isEqualTo(DARK)
  }

  @Test
  fun `when theme select dispatched, then mode is persisted`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(ThemeSelect(mode = DARK))
    runCurrent()

    coVerify { setThemeMode(mode = DARK) }
  }

  @Test
  fun `when edit username clicked, then sheet is shown`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)

    expectThat(viewModel.state.value.editSheet.isVisible).isTrue()
  }

  @Test
  fun `when edit username dismissed, then sheet is hidden`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(EditUsernameDismiss)

    expectThat(viewModel.state.value.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when username changed, then sheet username reflects input`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = USERNAME_JOHN))

    expectThat(viewModel.state.value.editSheet.username).isEqualTo(USERNAME_JOHN)
  }

  @Test
  fun `given non-blank draft, when submit dispatched, then username is persisted`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = USERNAME_JOHN))
    viewModel.dispatch(EditUsernameSubmit)
    runCurrent()

    coVerify { saveUsername(USERNAME_JOHN) }
  }

  @Test
  fun `given blank draft, when submit dispatched, then save is not invoked`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = "   "))
    viewModel.dispatch(EditUsernameSubmit)
    runCurrent()

    coVerify(exactly = 0) { saveUsername(any()) }
  }

  @Test
  fun `when submit dispatched, then sheet is hidden`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = USERNAME_JOHN))
    viewModel.dispatch(EditUsernameSubmit)

    expectThat(viewModel.state.value.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when licenses clicked, then open licenses is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(LicensesClick)
      expectThat(awaitItem()).isA<OpenLicenses>()
    }
  }

  @Test
  fun `when contact clicked, then open contact is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(ContactClick)
      expectThat(awaitItem()).isA<OpenContact>()
    }
  }

  @Test
  fun `when personalization clicked, then open personalization is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(PersonalizationClick)
      expectThat(awaitItem()).isA<OpenPersonalization>()
    }
  }

  @Test
  fun `when notifications clicked, then open notifications is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(NotificationsClick)
      expectThat(awaitItem()).isA<OpenNotifications>()
    }
  }

  @Test
  fun `when privacy clicked, then open privacy is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(PrivacyClick)
      expectThat(awaitItem()).isA<OpenPrivacy>()
    }
  }

  @Test
  fun `when locations stat clicked, then open locations is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(StatClick(type = LOCATIONS))
      expectThat(awaitItem()).isA<OpenLocations>()
    }
  }

  @Test
  fun `when morning brief stat clicked, then open notifications is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(StatClick(type = MORNING_BRIEF))
      expectThat(awaitItem()).isA<OpenNotifications>()
    }
  }

  @Test
  fun `when alerts stat clicked, then open notifications is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(StatClick(type = ALERTS))
      expectThat(awaitItem()).isA<OpenNotifications>()
    }
  }

  @Test
  fun `when vibe row clicked, then open vibe history is emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(VibeRowClick)
      expectThat(awaitItem()).isA<OpenVibeHistory>()
    }
  }

  private fun createViewModel(): ProfileViewModel =
    ProfileViewModel(stateFactory = stateFactory, useCases = useCases)
}
