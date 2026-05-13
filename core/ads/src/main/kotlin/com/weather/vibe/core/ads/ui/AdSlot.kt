package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.ads.ui.AdsDefaults.BannerHeight
import com.weather.vibe.domain.ads.placement.AdPlacement
import com.weather.vibe.domain.ads.provider.AdUnitIdProvider
import com.weather.vibe.domain.ads.usecase.ObserveAdSlotVisibility
import org.koin.compose.koinInject

@Composable
fun AdSlot(
  modifier: Modifier = Modifier,
  state: AdSlotState
) {
  if (!state.configVisible) {
    LaunchedEffect(state) { state.isLoaded = false }
    return
  }
  StatelessAdSlot(
    modifier = modifier,
    adUnitId = state.adUnitId,
    isLoaded = state.isLoaded,
    onAdFailed = { state.isLoaded = false },
    onAdLoaded = { state.isLoaded = true }
  )
}

@Composable
fun rememberAdSlotState(placement: AdPlacement): AdSlotState {

  if (LocalInspectionMode.current) return AdSlotState.Hidden

  val observeVisibility = koinInject<ObserveAdSlotVisibility>()
  val adUnitIdProvider = koinInject<AdUnitIdProvider>()
  val visibilityFlow = remember(placement) { observeVisibility(placement) }
  val configVisible by visibilityFlow.collectAsStateWithLifecycle(initialValue = false)
  val state = remember(placement) { AdSlotState(adUnitId = adUnitIdProvider.idFor(placement)) }
  state.configVisible = configVisible
  return state
}

@Composable
fun adSlotBottomInset(state: AdSlotState): Dp =
  if (state.isShown) BannerHeight else 0.dp
