package com.weather.vibe.feature.settings.notifications.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SetMorningBriefEnabled
import com.weather.vibe.domain.settings.usecase.SetWeatherAlertsEnabled
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.AlertsToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.BackClick
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.MorningBriefToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionDenied
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionLost
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.NavigateBack
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.OpenSystemNotificationSettings
import com.weather.vibe.feature.settings.notifications.presentation.fake.fakeNotificationsResources
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
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
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationsViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val setMorningBriefEnabled = mockk<SetMorningBriefEnabled>()
  private val setWeatherAlertsEnabled = mockk<SetWeatherAlertsEnabled>()
  private val stateFactory = NotificationsStateFactory(resources = fakeNotificationsResources())
  private val useCases = NotificationsUseCases(
    observeUserSettings = observeUserSettings,
    setMorningBriefEnabled = setMorningBriefEnabled,
    setWeatherAlertsEnabled = setWeatherAlertsEnabled
  )

  @Before
  fun setUp() {
    every { observeUserSettings() } returns emptyFlow()
    coJustRun { setMorningBriefEnabled(any()) }
    coJustRun { setWeatherAlertsEnabled(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when settings emitted, then alerts reflect enabled flag`() = runTest {

    every { observeUserSettings() } returns flowOf(
      Result.success(userSettings(alertsEnabled = true))
    )

    val viewModel = createViewModel()
    runCurrent()

    val loaded = viewModel.state.value as NotificationsUiState.Loaded
    expectThat(loaded.alertsEnabled).isTrue()
  }

  @Test
  fun `when settings emit fails, then state switched to error`() = runTest {

    every { observeUserSettings() } returns flowOf(Result.failure(RuntimeException("boom")))

    val viewModel = createViewModel()
    runCurrent()

    expectThat(viewModel.state.value).isA<NotificationsUiState.Error>()
  }

  @Test
  fun `when alerts toggled on, then weather alerts enabled`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(AlertsToggle(enabled = true))
    runCurrent()

    coVerify { setWeatherAlertsEnabled(true) }
  }

  @Test
  fun `when morning brief toggled on, then morning brief enabled`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(MorningBriefToggle(enabled = true))
    runCurrent()

    coVerify { setMorningBriefEnabled(true) }
  }

  @Test
  fun `when permission lost, then all notifications disabled`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(NotificationPermissionLost)
    runCurrent()

    coVerify { setWeatherAlertsEnabled(false) }
    coVerify { setMorningBriefEnabled(false) }
  }

  @Test
  fun `when permission denied, then open system settings event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(NotificationPermissionDenied)

      expectThat(awaitItem()).isA<OpenSystemNotificationSettings>()
    }
  }

  @Test
  fun `when back clicked, then navigate back event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(BackClick)

      expectThat(awaitItem()).isA<NavigateBack>()
    }
  }

  private fun createViewModel(): NotificationsViewModel =
    NotificationsViewModel(
      stateFactory = stateFactory,
      useCases = useCases
    )
}
