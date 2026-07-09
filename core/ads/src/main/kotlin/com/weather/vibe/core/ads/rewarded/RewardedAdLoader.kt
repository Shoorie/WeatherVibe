package com.weather.vibe.core.ads.rewarded

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.EARNED
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.FAILED
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal suspend fun showRewardedAd(
  activity: Activity,
  adUnitId: String
): RewardedAdOutcome =
  suspendCancellableCoroutine { continuation ->

    fun settle(outcome: RewardedAdOutcome) {
      if (continuation.isActive) continuation.resume(outcome)
    }

    loadRewardedAd(
      activity = activity,
      adUnitId = adUnitId,
      onFailed = { settle(FAILED) },
      onLoaded = { ad -> ad.presentFullScreen(activity = activity, onOutcome = ::settle) }
    )
  }

private fun loadRewardedAd(
  activity: Activity,
  adUnitId: String,
  onFailed: () -> Unit,
  onLoaded: (RewardedAd) -> Unit
) {
  RewardedAd.load(
    activity,
    adUnitId,
    AdRequest.Builder().build(),
    object : RewardedAdLoadCallback() {
      override fun onAdFailedToLoad(error: LoadAdError) = onFailed()
      override fun onAdLoaded(ad: RewardedAd) = onLoaded(ad)
    }
  )
}

private fun RewardedAd.presentFullScreen(
  activity: Activity,
  onOutcome: (RewardedAdOutcome) -> Unit
) {
  fullScreenContentCallback = RewardedDismissalCallback(onOutcome = onOutcome)
  show(activity) { onOutcome(EARNED) }
}
