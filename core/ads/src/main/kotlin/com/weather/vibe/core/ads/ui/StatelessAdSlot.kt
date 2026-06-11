package com.weather.vibe.core.ads.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.weather.vibe.core.ads.ui.AdsDefaults.BannerHeight
import com.weather.vibe.core.ads.ui.AdsResources.Texts.previewPlaceholder
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
internal fun StatelessAdSlot(
  modifier: Modifier = Modifier,
  adUnitId: String,
  isLoaded: Boolean,
  onAdFailed: () -> Unit,
  onAdLoaded: () -> Unit
) {

  if (LocalInspectionMode.current) {
    AdSlotPreviewPlaceholder(modifier = modifier)
    return
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .then(if (isLoaded) Modifier.windowInsetsPadding(WindowInsets.navigationBars) else Modifier)
      .height(if (isLoaded) BannerHeight else 0.dp)
      .clipToBounds()
  ) {
    AndroidView(
      modifier = Modifier
        .fillMaxWidth()
        .height(BannerHeight),
      factory = { context ->
        createAdView(
          adUnitId = adUnitId,
          context = context,
          onAdFailed = onAdFailed,
          onAdLoaded = onAdLoaded
        )
      }
    )
  }
}

@Composable
private fun AdSlotPreviewPlaceholder(modifier: Modifier = Modifier) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .fillMaxWidth()
      .height(BannerHeight)
      .background(colors.surfaceVariant)
  ) {
    Text(
      color = colors.onSurfaceVariant,
      style = typography.labelMedium,
      text = previewPlaceholder()
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    StatelessAdSlot(
      adUnitId = "preview",
      isLoaded = false,
      onAdFailed = {},
      onAdLoaded = {}
    )
  }
}
