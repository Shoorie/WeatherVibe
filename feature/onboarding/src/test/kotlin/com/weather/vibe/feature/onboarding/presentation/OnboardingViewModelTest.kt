package com.weather.vibe.feature.onboarding.presentation

import app.cash.turbine.test
import com.weather.vibe.domain.location.usecase.ObtainCurrentLocation
import com.weather.vibe.domain.location.usecase.PersistSelectedLocation
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.OpenSystemSettingsClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.PermissionResult
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.SearchCityClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.UseMyLocationClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.NavigateToHome
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.NavigateToSearch
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.OpenAppSettings
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.RequestPermission
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.FETCHING_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.IDLE
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.PERMISSION_PERMANENTLY_DENIED
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.REQUESTING_PERMISSION
import com.weather.vibe.feature.onboarding.ui.OnboardingResources
import com.weather.vibe.testing.coroutines.MainDispatcherRule
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import io.mockk.coEvery
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
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

  @get:Rule
  val rule = MainDispatcherRule()

  private val obtainCurrentLocation = mockk<ObtainCurrentLocation>()
  private val persistSelectedLocation = mockk<PersistSelectedLocation>()
  private val resources = mockk<OnboardingResources>()
  private val stateFactory = OnboardingStateFactory(resources)

  @Before
  fun setUp() {
    every { resources.subtitleFor(any()) } returns ""
    every { resources.primaryLabelFor(any()) } returns ""
    coJustRun { persistSelectedLocation(any()) }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when created, then phase is idle`() = runTest {

    val viewModel = createViewModel()

    expectThat(viewModel.state.value.phase).isEqualTo(IDLE)
  }

  @Test
  fun `when use my location clicked, then phase is requesting permission`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(UseMyLocationClick)

    expectThat(viewModel.state.value.phase).isEqualTo(REQUESTING_PERMISSION)
  }

  @Test
  fun `when use my location clicked, then request permission event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(UseMyLocationClick)

      expectThat(awaitItem()).isA<RequestPermission>()
    }
  }

  @Test
  fun `when search city clicked, then navigate to search event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(SearchCityClick)

      expectThat(awaitItem()).isA<NavigateToSearch>()
    }
  }

  @Test
  fun `given soft permission denial, when permission result arrives, then phase resets to idle`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(UseMyLocationClick)
    viewModel.dispatch(PermissionResult(granted = false, canAskAgain = true))

    expectThat(viewModel.state.value.phase).isEqualTo(IDLE)
  }

  @Test
  fun `given permanent permission denial, when permission result arrives, then phase is permanently denied`() = runTest {

    val viewModel = createViewModel()

    viewModel.dispatch(UseMyLocationClick)
    viewModel.dispatch(PermissionResult(granted = false, canAskAgain = false))

    expectThat(viewModel.state.value.phase).isEqualTo(PERMISSION_PERMANENTLY_DENIED)
  }

  @Test
  fun `when open system settings clicked, then open app settings event emitted`() = runTest {

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(OpenSystemSettingsClick)

      expectThat(awaitItem()).isA<OpenAppSettings>()
    }
  }

  @Test
  fun `given permanently denied and permission granted externally, when result arrives, then fetch runs`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.success(WARSAW))

    val viewModel = createViewModel()

    viewModel.dispatch(PermissionResult(granted = false, canAskAgain = false))
    viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))
    runCurrent()

    coVerify { persistSelectedLocation(WARSAW) }
  }

  @Test
  fun `given permission granted and location resolved, when permission result arrives, then navigate to home with resolved location`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.success(WARSAW))

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))

      val event = awaitItem()
      expectThat(event).isA<NavigateToHome>()
        .get { location }.isEqualTo(WARSAW)
    }
  }

  @Test
  fun `given permission granted and location resolved, when permission result arrives, then location persisted`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.success(WARSAW))

    val viewModel = createViewModel()

    viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))
    runCurrent()

    coVerify { persistSelectedLocation(WARSAW) }
  }

  @Test
  fun `given permission granted and location being fetched, then phase is fetching location`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.success(WARSAW))

    val viewModel = createViewModel()

    viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))

    expectThat(viewModel.state.value.phase).isA<OnboardingPhase>()
  }

  @Test
  fun `given location fetch fails after permission granted, then phase resets to idle`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.failure(IOException("no gps")))

    val viewModel = createViewModel()

    viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))
    runCurrent()

    expectThat(viewModel.state.value.phase).isEqualTo(IDLE)
  }

  @Test
  fun `given location fetch fails after permission granted, then navigate to search event emitted`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.failure(IOException("no gps")))

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))

      expectThat(awaitItem()).isA<NavigateToSearch>()
    }
  }

  @Test
  fun `given persist throws, when location resolved, then navigate to search event emitted`() = runTest {

    every { obtainCurrentLocation() } returns flowOf(Result.success(WARSAW))
    coEvery { persistSelectedLocation(WARSAW) } throws IllegalStateException("db down")

    val viewModel = createViewModel()

    viewModel.event.test {
      viewModel.dispatch(PermissionResult(granted = true, canAskAgain = true))

      expectThat(awaitItem()).isA<NavigateToSearch>()
    }
  }

  private fun createViewModel(): OnboardingViewModel =
    OnboardingViewModel(
      obtainCurrentLocation = obtainCurrentLocation,
      persistSelectedLocation = persistSelectedLocation,
      stateFactory = stateFactory
    )
}
