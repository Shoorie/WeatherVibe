package com.weather.vibe.core.ads.rewarded

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.DISMISSED
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.FAILED

internal class RewardedDismissalCallback(
  private val onOutcome: (RewardedAdOutcome) -> Unit
) : FullScreenContentCallback() {

  override fun onAdDismissedFullScreenContent() {
    onOutcome(DISMISSED)
  }

  override fun onAdFailedToShowFullScreenContent(error: AdError) {
    onOutcome(FAILED)
  }
}
