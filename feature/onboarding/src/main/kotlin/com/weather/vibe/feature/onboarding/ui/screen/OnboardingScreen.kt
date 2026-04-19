package com.weather.vibe.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.LiveRegionMode.Companion.Polite
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.button.BrandButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.permissions.openAppDetailsSettings
import com.weather.vibe.core.permissions.rememberLocationPermissionGranted
import com.weather.vibe.core.permissions.rememberLocationPermissionRequester
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.NavigateToHome
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.NavigateToSearch
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.OpenAppSettings
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.RequestPermission
import com.weather.vibe.feature.onboarding.presentation.OnboardingViewModel
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.PERMISSION_PERMANENTLY_DENIED
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction.OPEN_SETTINGS
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction.USE_MY_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingUiState
import com.weather.vibe.feature.onboarding.preview.OnboardingPreview
import com.weather.vibe.feature.onboarding.ui.OnboardingTexts.headline
import com.weather.vibe.feature.onboarding.ui.OnboardingTexts.locationIndicatorA11y
import com.weather.vibe.feature.onboarding.ui.OnboardingTexts.privacyNote
import com.weather.vibe.feature.onboarding.ui.OnboardingTexts.secondaryCta
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.ContentMaxWidth
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HaloToHeadline
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.HeadlineToSubtitle
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.PrimaryToSecondaryCta
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.SecondaryCtaToPrivacy
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.SubtitleToPrimaryCta
import com.weather.vibe.feature.onboarding.ui.screen.callbacks.OnboardingCallbacks
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
  onNavigateToHome: (Location) -> Unit,
  onNavigateToSearch: () -> Unit
) {

  val context = LocalContext.current
  val viewModel: OnboardingViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val permissionGranted by rememberLocationPermissionGranted()
  val callbacks = remember(viewModel) { OnboardingCallbacks(viewModel) }

  val requestPermission = rememberLocationPermissionRequester(
    onGranted = { callbacks.onPermissionResult(true, true) },
    onDenied = { canAskAgain -> callbacks.onPermissionResult(false, canAskAgain) }
  )

  LaunchedEffect(permissionGranted, state.phase) {
    if (permissionGranted && state.phase == PERMISSION_PERMANENTLY_DENIED) {
      callbacks.onPermissionResult(true, true)
    }
  }

  LaunchedEffect(viewModel) {
    viewModel.event.collect { event ->
      when (event) {
        is RequestPermission -> requestPermission()
        is OpenAppSettings -> context.openAppDetailsSettings()
        is NavigateToHome -> onNavigateToHome(event.location)
        is NavigateToSearch -> onNavigateToSearch()
      }
    }
  }

  OnboardingContent(
    state = state,
    onUseMyLocation = callbacks.onUseMyLocation,
    onSearchCity = callbacks.onSearchCity,
    onOpenSettings = callbacks.onOpenSettings
  )
}

@Composable
internal fun OnboardingContent(
  modifier: Modifier = Modifier,
  state: OnboardingUiState,
  onUseMyLocation: () -> Unit,
  onSearchCity: () -> Unit,
  onOpenSettings: () -> Unit
) {

  val gradientStart = colors.backgroundGradientStart
  val gradientEnd = colors.backgroundGradientEnd
  val backgroundBrush = remember(gradientStart, gradientEnd) {
    Brush.verticalGradient(colors = listOf(gradientStart, gradientEnd))
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(backgroundBrush)
      .padding(horizontal = Large),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier.widthIn(max = ContentMaxWidth),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LocationHaloPulse(
        contentDescription = locationIndicatorA11y(),
        intensity = state.pulseIntensity
      )
      Spacer(modifier = Modifier.height(HaloToHeadline))
      Text(
        modifier = Modifier.semantics { heading() },
        text = headline(),
        style = typography.headlineMedium,
        color = colors.onBackground,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(HeadlineToSubtitle))
      Text(
        modifier = Modifier.semantics { liveRegion = Polite },
        text = state.subtitle,
        style = typography.bodyMedium,
        color = colors.onSurfaceVariant,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(SubtitleToPrimaryCta))
      OnboardingCtas(
        state = state,
        onUseMyLocation = onUseMyLocation,
        onSearchCity = onSearchCity,
        onOpenSettings = onOpenSettings
      )
      Spacer(modifier = Modifier.height(SecondaryCtaToPrivacy))
      Text(
        text = privacyNote(),
        style = typography.labelSmall,
        color = colors.textTertiary,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun OnboardingCtas(
  state: OnboardingUiState,
  onUseMyLocation: () -> Unit,
  onSearchCity: () -> Unit,
  onOpenSettings: () -> Unit
) {
  BrandButton(
    icon = primaryIconFor(state.primaryAction),
    text = state.primaryLabel,
    containerColor = colors.accent,
    enabled = state.primaryEnabled,
    onClick = primaryClickFor(
      action = state.primaryAction,
      onUseMyLocation = onUseMyLocation,
      onOpenSettings = onOpenSettings
    )
  )
  Spacer(modifier = Modifier.height(PrimaryToSecondaryCta))
  TextButton(
    modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    onClick = onSearchCity
  ) {
    Text(
      text = secondaryCta(),
      style = typography.titleMedium,
      color = colors.accent
    )
  }
}

@Composable
private fun primaryIconFor(action: OnboardingPrimaryAction): Painter =
  when (action) {
    OPEN_SETTINGS -> rememberVectorPainter(Icons.Filled.Settings)
    USE_MY_LOCATION -> rememberVectorPainter(Icons.Filled.LocationOn)
  }

private fun primaryClickFor(
  action: OnboardingPrimaryAction,
  onUseMyLocation: () -> Unit,
  onOpenSettings: () -> Unit
): () -> Unit =
  when (action) {
    OPEN_SETTINGS -> onOpenSettings
    USE_MY_LOCATION -> onUseMyLocation
  }

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(OnboardingPreview::class)
  state: OnboardingUiState
) {
  WeatherVibeTheme {
    OnboardingContent(
      state = state,
      onUseMyLocation = {},
      onSearchCity = {},
      onOpenSettings = {}
    )
  }
}
