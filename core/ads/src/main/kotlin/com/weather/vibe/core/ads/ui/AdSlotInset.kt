package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.ads.domain.AdPlacement
import com.weather.vibe.core.ads.domain.usecase.ObserveAdSlotVisibility
import com.weather.vibe.core.ads.ui.AdsDefaults.BannerHeight
import org.koin.compose.koinInject

@Composable
fun rememberAdSlotBottomInset(placement: AdPlacement): Dp {
  if (LocalInspectionMode.current) return 0.dp
  val observeVisibility = koinInject<ObserveAdSlotVisibility>()
  val visibilityFlow = remember(placement) { observeVisibility(placement) }
  val isVisible by visibilityFlow.collectAsStateWithLifecycle(initialValue = false)
  return if (isVisible) BannerHeight else 0.dp
}
