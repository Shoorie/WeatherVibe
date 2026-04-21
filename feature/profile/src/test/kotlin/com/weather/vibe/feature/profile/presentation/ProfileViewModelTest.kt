package com.weather.vibe.feature.profile.presentation

import android.util.Log
import app.cash.turbine.test
import com.weather.vibe.domain.profile.usecase.ObserveProfile
import com.weather.vibe.domain.profile.usecase.SaveUsername
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.feature.profile.presentation.ProfileAction.AboutClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameDismiss
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameSubmit
import com.weather.vibe.feature.profile.presentation.ProfileAction.NotificationsClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PersonalizationClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PrivacyClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.UsernameChanged
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenAbout
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenNotifications
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPersonalization
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPrivacy
import com.weather.vibe.feature.profile.presentation.fake.fakeProfileResources
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.TONE_LABEL_FORMAL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.USERNAME_JOHN
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.greeting
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.profileSummary
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val observeProfile = mockk<ObserveProfile>()
  private val saveUsername = mockk<SaveUsername>()
  private val stateFactory = ProfileStateFactory(resources = fakeProfileResources())
  private val useCases = ProfileUseCases(
    observeProfile = observeProfile,
    observeUserSettings = observeUserSettings,
    saveUsername = saveUsername
  )

  @Before
  fun setUp() {
    mockkStatic(Log::class)
    every { Log.e(any(), any(), any()) } returns 0
    every { observeUserSettings() } returns emptyFlow()
    every { observeProfile() } returns emptyFlow()
    coJustRun { saveUsername(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when profile emitted, then greeting updates`() = runTest {

    every { observeProfile() } returns flowOf(profileSummary(usageDays = 42))
    every { observeUserSettings() } returns flowOf(success(userSettings()))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.greeting)
      .isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `when profile emitted, then streak stat updates`() = runTest {

    every { observeProfile() } returns flowOf(profileSummary(usageDays = 42))
    every { observeUserSettings() } returns flowOf(success(userSettings()))

    val viewModel = createViewModel()
    runCurrent()

    val streak = viewModel.state.value.quickStats.first { it.id == "streak" }
    expectThat(streak.value).isEqualTo("42")
  }

  @Test
  fun `when brief tone emitted, then label updates`() = runTest {

    every { observeProfile() } returns flowOf(profileSummary(usageDays = 1))
    every { observeUserSettings() } returns flowOf(success(userSettings(briefTone = FORMAL)))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.briefToneLabel)
      .isEqualTo(TONE_LABEL_FORMAL)
  }

  @Test
  fun `given settings failure, when profile emitted, then header still updates`() = runTest {

    every { observeProfile() } returns flowOf(
      profileSummary(usageDays = 7)
    )
    every { observeUserSettings() } returns flowOf(failure(RuntimeException("boom")))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value.header.greeting)
      .isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `when edit username clicked, then sheet shown`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)

    expectThat(viewModel.state.value.editSheet.isVisible).isTrue()
  }

  @Test
  fun `when edit username dismissed, then sheet hidden`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(EditUsernameDismiss)

    expectThat(viewModel.state.value.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when username changed in sheet, then sheet value reflects input`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = USERNAME_JOHN))

    expectThat(viewModel.state.value.editSheet.username)
      .isEqualTo(USERNAME_JOHN)
  }

  @Test
  fun `given non-blank draft, when submit dispatched, then save invoked`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = USERNAME_JOHN))
    viewModel.dispatch(EditUsernameSubmit)
    runCurrent()

    coVerify { saveUsername(USERNAME_JOHN) }
  }

  @Test
  fun `given blank draft, when submit dispatched, then save not invoked`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = "   "))
    viewModel.dispatch(EditUsernameSubmit)
    runCurrent()

    coVerify(exactly = 0) { saveUsername(any()) }
  }

  @Test
  fun `when submit dispatched, then sheet hidden`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(EditUsernameClick)
    viewModel.dispatch(UsernameChanged(value = USERNAME_JOHN))
    viewModel.dispatch(EditUsernameSubmit)

    expectThat(viewModel.state.value.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when about clicked, then open about event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {

      viewModel.dispatch(AboutClick)

      expectThat(awaitItem()).isA<OpenAbout>()
    }
  }

  @Test
  fun `when personalization clicked, then open personalization event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {

      viewModel.dispatch(PersonalizationClick)

      expectThat(awaitItem()).isA<OpenPersonalization>()
    }
  }

  @Test
  fun `when notifications clicked, then open notifications event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {

      viewModel.dispatch(NotificationsClick)

      expectThat(awaitItem()).isA<OpenNotifications>()
    }
  }

  @Test
  fun `when privacy clicked, then open privacy event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {

      viewModel.dispatch(PrivacyClick)

      expectThat(awaitItem()).isA<OpenPrivacy>()
    }
  }

  private fun createViewModel(): ProfileViewModel =
    ProfileViewModel(
      stateFactory = stateFactory,
      useCases = useCases
    )
}
