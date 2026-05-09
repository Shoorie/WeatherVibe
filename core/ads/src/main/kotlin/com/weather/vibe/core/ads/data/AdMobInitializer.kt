package com.weather.vibe.core.ads.data

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.weather.vibe.core.ads.consent.ConsentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import org.koin.core.annotation.Single

@Single
class AdMobInitializer(
  private val applicationContext: Context,
  private val consentManager: ConsentManager,
  private val testDeviceIdsProvider: TestDeviceIdsProvider
) {

  private val scope = CoroutineScope(Dispatchers.Main.immediate)

  fun start() {
    applyTestDevices()
    consentManager.canRequestAds
      .distinctUntilChanged()
      .filter { canRequest -> canRequest }
      .take(1)
      .onEach { initializeMobileAds() }
      .launchIn(scope)
  }

  private fun applyTestDevices() {
    val deviceIds = testDeviceIdsProvider.deviceIds()
    if (deviceIds.isEmpty()) return
    MobileAds.setRequestConfiguration(
      RequestConfiguration.Builder()
        .setTestDeviceIds(deviceIds)
        .build()
    )
  }

  private fun initializeMobileAds() {
    MobileAds.initialize(applicationContext) { }
  }
}
