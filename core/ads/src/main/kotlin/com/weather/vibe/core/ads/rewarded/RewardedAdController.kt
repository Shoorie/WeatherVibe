package com.weather.vibe.core.ads.rewarded

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.weather.vibe.domain.ads.placement.AdPlacement
import com.weather.vibe.domain.ads.provider.AdUnitIdProvider
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.EARNED
import com.weather.vibe.domain.ads.rewarded.RewardedAdOutcome.FAILED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class RewardedAdController internal constructor(
  private val activity: Activity?,
  private val adUnitIdProvider: AdUnitIdProvider
) {

  var isWatching by mutableStateOf(false)
    private set

  fun rewardOnWatch(
    scope: CoroutineScope,
    placement: AdPlacement,
    onEarned: () -> Unit
  ) {
    if (isWatching) return
    isWatching = true
    scope.launch {
      try {
        if (show(placement) == EARNED) onEarned()
      } finally {
        isWatching = false
      }
    }
  }

  private suspend fun show(placement: AdPlacement): RewardedAdOutcome {
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
