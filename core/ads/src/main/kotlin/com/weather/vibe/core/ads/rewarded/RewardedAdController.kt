package com.weather.vibe.core.ads.rewarded

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.weather.vibe.domain.ads.placement.AdPlacement
import com.weather.vibe.domain.ads.provider.AdUnitIdProvider
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.FAILED
import org.koin.compose.koinInject

class RewardedAdController internal constructor(
  private val activity: Activity?,
  private val adUnitIdProvider: AdUnitIdProvider
) {

  suspend fun show(placement: AdPlacement): RewardedAdOutcome {
    val host = activity ?: return FAILED
    return showRewardedAd(
      activity = host,
      adUnitId = adUnitIdProvider.idFor(placement)
    )
  }
}

@Composable
fun rememberRewardedAdController(): RewardedAdController {

  val activity = LocalActivity.current
  val adUnitIdProvider = koinInject<AdUnitIdProvider>()

  return remember(activity) {
    RewardedAdController(
      activity = activity,
      adUnitIdProvider = adUnitIdProvider
    )
  }
}
