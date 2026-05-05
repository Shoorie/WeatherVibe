package com.weather.vibe.feature.onboarding.ui.screen.welcome

import android.Manifest.permission.POST_NOTIFICATIONS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.permissions.notification.NotificationPermissionSupport
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeEvent.NavigateToLocationOnboarding
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeEvent.RequestNotificationsPermission
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeViewModel
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlides.LAST_INDEX
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeUiState
import com.weather.vibe.feature.onboarding.preview.welcome.WelcomePreviewProvider
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.SkipEndPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.SkipTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.callbacks.WelcomeCallbacks
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.WelcomeFooter
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.WelcomeSkipButton
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeResources
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeOnboardingScreen(onFinishWelcome: () -> Unit) {

  val viewModel: WelcomeViewModel = koinViewModel()
  val resources: WelcomeResources = koinInject()
  val permissionSupport: NotificationPermissionSupport = koinInject()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = remember(viewModel) { WelcomeCallbacks(viewModel) }
  val pagerState = rememberPagerState(pageCount = { state.totalSlides })

  val permissionLauncher = rememberLauncherForActivityResult(
    contract = RequestPermission(),
    onResult = callbacks.onNotificationsPermissionResult
  )

  LaunchedEffect(viewModel) {
    viewModel.event.collect { event ->
      when (event) {
        is NavigateToLocationOnboarding -> onFinishWelcome()
        is RequestNotificationsPermission -> when (permissionSupport.isSupported()) {
          true -> permissionLauncher.launch(POST_NOTIFICATIONS)
          false -> callbacks.onNotificationsPermissionResult(true)
        }
      }
    }
  }

  LaunchedEffect(state.slideIndex) {
    if (pagerState.currentPage != state.slideIndex) {
      pagerState.animateScrollToPage(state.slideIndex)
    }
  }

  LaunchedEffect(pagerState) {
    snapshotFlow { pagerState.settledPage }
      .collect(callbacks.onSlideChange)
  }

  WelcomeOnboardingContent(
    state = state,
    pagerState = pagerState,
    skipLabel = resources.skipLabel(),
    onNextClick = callbacks.onNextClick,
    onSkipClick = callbacks.onSkipClick,
    onSkipNotificationsClick = callbacks.onSkipNotificationsClick
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WelcomeOnboardingContent(
  modifier: Modifier = Modifier,
  state: WelcomeUiState,
  pagerState: PagerState,
  skipLabel: String,
  onNextClick: () -> Unit,
  onSkipClick: () -> Unit,
  onSkipNotificationsClick: () -> Unit
) {

  val dotPosition by remember(pagerState) {
    derivedStateOf { pagerState.currentPage + pagerState.currentPageOffsetFraction }
  }
  val skipVisible by remember(pagerState) {
    derivedStateOf { dotPosition < LAST_INDEX }
  }

  Box(modifier = modifier.fillMaxSize()) {
    WelcomeSlideHost(pagerState = pagerState, state = state)

    if (skipVisible && state.skipVisible) {
      WelcomeSkipButton(
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(top = SkipTopPadding, end = SkipEndPadding),
        label = skipLabel,
        onClick = onSkipClick
      )
    }

    WelcomeFooter(
      modifier = Modifier.align(Alignment.BottomCenter),
      dotPosition = dotPosition,
      primaryActionLabel = state.primaryActionLabel,
      skipNotificationsLabel = state.skipNotificationsLabel,
      totalSlides = state.totalSlides,
      onPrimaryActionClick = onNextClick,
      onSkipNotificationsClick = onSkipNotificationsClick
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WelcomePreviewProvider::class)
  state: WelcomeUiState
) {
  WeatherVibeTheme {
    val pagerState = rememberPagerState(
      initialPage = state.slideIndex,
      pageCount = { state.totalSlides }
    )
    WelcomeOnboardingContent(
      state = state,
      pagerState = pagerState,
      skipLabel = "Skip",
      onNextClick = {},
      onSkipClick = {},
      onSkipNotificationsClick = {}
    )
  }
}
