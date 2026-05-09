package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.ads.data.AdUnitIdProvider
import com.weather.vibe.core.ads.domain.AdPlacement
import com.weather.vibe.core.ads.domain.usecase.ObserveAdSlotVisibility
import org.koin.compose.koinInject

@Composable
fun AdSlot(placement: AdPlacement, modifier: Modifier = Modifier) {
  val observeVisibility = koinInject<ObserveAdSlotVisibility>()
  val adUnitIdProvider = koinInject<AdUnitIdProvider>()
  val visibilityFlow = remember(placement) { observeVisibility(placement) }
  val isVisible by visibilityFlow.collectAsStateWithLifecycle(initialValue = false)
  if (!isVisible) return
  val adUnitId = remember(placement) { adUnitIdProvider.idFor(placement) }
  StatelessAdSlot(modifier = modifier, adUnitId = adUnitId)
}
