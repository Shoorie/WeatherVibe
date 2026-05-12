package com.weather.vibe.core.ads.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.weather.vibe.core.ads.ui.AdsDefaults.BannerHeight
import com.weather.vibe.core.ads.ui.AdsResources.Texts.previewPlaceholder
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
internal fun StatelessAdSlot(
  modifier: Modifier = Modifier,
  adUnitId: String
) {
  if (LocalInspectionMode.current) {
    AdSlotPreviewPlaceholder(modifier = modifier)
    return
  }
  AndroidView(
    modifier = modifier
      .fillMaxWidth()
      .height(BannerHeight),
    factory = { context ->
      AdView(context).apply {
        setAdUnitId(adUnitId)
        setAdSize(AdSize.BANNER)
        loadAd(AdRequest.Builder().build())
      }
    }
  )
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
    StatelessAdSlot(adUnitId = "preview")
  }
}
