package com.weather.vibe.feature.onboarding.presentation.welcome

import app.cash.turbine.test
import com.weather.vibe.core.permissions.notification.NotificationPermissionSupport
import com.weather.vibe.domain.settings.usecase.EnableDefaultNotifications
import com.weather.vibe.domain.settings.usecase.MarkWelcomeOnboardingSeen
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.NextClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.NotificationsPermissionResult
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SkipClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SkipNotificationsClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeEvent.NavigateToLocationOnboarding
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeEvent.RequestNotificationsPermission
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.READY
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.TALK
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlides
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeResources
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val enableDefaultNotifications = mockk<EnableDefaultNotifications>()
  private val markWelcomeOnboardingSeen = mockk<MarkWelcomeOnboardingSeen>()
  private val notificationPermissionSupport = mockk<NotificationPermissionSupport>()
  private val resources = mockk<WelcomeResources>(relaxed = true)
  private val stateFactory = WelcomeStateFactory(
    notificationPermission = notificationPermissionSupport,
    resources = resources
  )

  @Before
  fun setUp() {
    every { resources.nextLabel() } returns ""
    every { resources.enableNotificationsAndFinishLabel() } returns ""
    every { resources.finishLabel() } returns ""
    every { resources.skipNotificationsLabel() } returns "Maybe later"
    every { notificationPermissionSupport.isSupported() } returns true
    coJustRun { enableDefaultNotifications() }
    coJustRun { markWelcomeOnboardingSeen() }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when created, then slide is talk`() = runTest {

    val viewModel = createViewModel()

    expectThat(viewModel.state.value.slide).isEqualTo(TALK)
  }

  @Test
  fun `when next clicked, then slide index advances by one`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(NextClick)

    expectThat(viewModel.state.value.slideIndex).isEqualTo(1)
  }

  @Test
  fun `given last slide reached, when next clicked, then permission request event emitted`() =
    runTest {

      val viewModel = createViewModel()

      repeat(WelcomeSlides.LAST_INDEX) { viewModel.dispatch(NextClick) }

      viewModel.event.test {
        viewModel.dispatch(NextClick)

        expectThat(awaitItem()).isA<RequestNotificationsPermission>()
      }
    }

  @Test
  fun `when skip clicked, then slide jumps to last index`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(SkipClick)

    expectThat(viewModel.state.value.slideIndex).isEqualTo(WelcomeSlides.LAST_INDEX)
  }

  @Test
  fun `when skip clicked, then slide is ready`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(SkipClick)

    expectThat(viewModel.state.value.slide).isEqualTo(READY)
  }

  @Test
  fun `when permission granted, then default notifications enabled`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(NotificationsPermissionResult(granted = true))

    coVerify { enableDefaultNotifications() }
  }

  @Test
  fun `when permission granted, then welcome onboarding marked seen`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(NotificationsPermissionResult(granted = true))

    coVerify { markWelcomeOnboardingSeen() }
  }

  @Test
  fun `when permission granted, then navigate to location event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(NotificationsPermissionResult(granted = true))

      expectThat(awaitItem()).isA<NavigateToLocationOnboarding>()
    }
  }

  @Test
  fun `when permission denied, then default notifications stay disabled`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(NotificationsPermissionResult(granted = false))

    coVerify(exactly = 0) { enableDefaultNotifications() }
  }

  @Test
  fun `when permission denied, then welcome onboarding marked seen`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(NotificationsPermissionResult(granted = false))

    coVerify { markWelcomeOnboardingSeen() }
  }

  @Test
  fun `when permission denied, then navigate to location event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(NotificationsPermissionResult(granted = false))

      expectThat(awaitItem()).isA<NavigateToLocationOnboarding>()
    }
  }

  @Test
  fun `when skip notifications clicked, then default notifications stay disabled`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(SkipNotificationsClick)

    coVerify(exactly = 0) { enableDefaultNotifications() }
  }

  @Test
  fun `when skip notifications clicked, then welcome onboarding marked seen`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(SkipNotificationsClick)

    coVerify { markWelcomeOnboardingSeen() }
  }

  private fun createViewModel(): WelcomeViewModel =
    WelcomeViewModel(
      enableDefaultNotifications = enableDefaultNotifications,
      markWelcomeOnboardingSeen = markWelcomeOnboardingSeen,
      stateFactory = stateFactory
    )
}
