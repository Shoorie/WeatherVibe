package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Composable
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
  state: AdSlotUiState
) {

  if (!state.isVisible) return

  StatelessAdSlot(
    modifier = modifier,
    adUnitId = state.adUnitId
  )
}

@Composable
fun rememberAdSlotUiState(placement: AdPlacement): AdSlotUiState {

  if (LocalInspectionMode.current) return AdSlotUiState.Hidden

  val observeVisibility = koinInject<ObserveAdSlotVisibility>()
  val adUnitIdProvider = koinInject<AdUnitIdProvider>()
  val visibilityFlow = remember(placement) { observeVisibility(placement) }
  val isVisible by visibilityFlow.collectAsStateWithLifecycle(initialValue = false)

  return remember(isVisible, placement) {
    AdSlotUiState(
      adUnitId = adUnitIdProvider.idFor(placement),
      isVisible = isVisible
    )
  }
}

@Composable
fun adSlotBottomInset(state: AdSlotUiState): Dp =
  if (state.isVisible) BannerHeight else 0.dp
