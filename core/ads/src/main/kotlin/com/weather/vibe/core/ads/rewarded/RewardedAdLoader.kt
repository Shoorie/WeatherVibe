package com.weather.vibe.core.ads.rewarded

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.DISMISSED
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.EARNED
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.FAILED
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal suspend fun showRewardedAd(
  activity: Activity,
  adUnitId: String
): RewardedAdOutcome =
  suspendCancellableCoroutine { continuation ->

    fun finish(outcome: RewardedAdOutcome) {
      if (continuation.isActive) continuation.resume(outcome)
    }

    RewardedAd.load(
      activity,
      adUnitId,
      AdRequest.Builder().build(),
      object : RewardedAdLoadCallback() {

        override fun onAdFailedToLoad(error: LoadAdError) {
          finish(FAILED)
        }

        override fun onAdLoaded(ad: RewardedAd) {
          var rewardEarned = false
          ad.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
              finish(if (rewardEarned) EARNED else DISMISSED)
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
              finish(FAILED)
            }
          }
          ad.show(activity) { rewardEarned = true }
        }
      }
    )
  }
