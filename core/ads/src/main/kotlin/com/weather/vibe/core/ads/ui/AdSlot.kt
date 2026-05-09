package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.ads.data.AdUnitIdProvider
import com.weather.vibe.core.ads.domain.AdPlacement
import com.weather.vibe.core.ads.domain.usecase.ObserveAdSlotVisibility
import com.weather.vibe.core.ads.ui.AdsDefaults.BannerHeight
import org.koin.compose.koinInject

@Composable
fun AdSlot(state: AdSlotState, modifier: Modifier = Modifier) {
  if (!state.isVisible) return
  StatelessAdSlot(modifier = modifier, adUnitId = state.adUnitId)
}

@Composable
fun rememberAdSlotState(placement: AdPlacement): AdSlotState {
  if (LocalInspectionMode.current) return AdSlotState.Hidden
  val observeVisibility = koinInject<ObserveAdSlotVisibility>()
  val adUnitIdProvider = koinInject<AdUnitIdProvider>()
  val visibilityFlow = remember(placement) { observeVisibility(placement) }
  val isVisible by visibilityFlow.collectAsStateWithLifecycle(initialValue = false)
  return remember(isVisible, placement) {
    AdSlotState(
      adUnitId = adUnitIdProvider.idFor(placement),
      bottomInset = if (isVisible) BannerHeight else 0.dp,
      isVisible = isVisible
    )
  }
}
