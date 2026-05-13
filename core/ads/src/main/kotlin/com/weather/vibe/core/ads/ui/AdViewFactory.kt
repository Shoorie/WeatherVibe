package com.weather.vibe.core.ads.ui

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

internal fun createAdView(
  adUnitId: String,
  context: Context,
  adSize: AdSize = AdSize.BANNER,
  onAdFailed: () -> Unit,
  onAdLoaded: () -> Unit
): AdView =
  AdView(context).apply {

    setAdUnitId(adUnitId)
    setAdSize(adSize)

    adListener = AdLoadListener(
      onAdFailed = onAdFailed,
      onAdLoaded = onAdLoaded
    )
    loadAd(AdRequest.Builder().build())
  }

private class AdLoadListener(
  private val onAdFailed: () -> Unit,
  private val onAdLoaded: () -> Unit
) : AdListener() {

  override fun onAdLoaded() {
    onAdLoaded.invoke()
  }

  override fun onAdFailedToLoad(error: LoadAdError) {
    onAdFailed()
  }
}
