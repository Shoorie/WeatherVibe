package com.weather.vibe.core.ads.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.weather.vibe.core.ads.consent.ConsentManager
import org.koin.compose.koinInject

@Composable
fun ConsentManagerHost() {
  val consentManager = koinInject<ConsentManager>()
  val activity = LocalActivity.current ?: return
  LaunchedEffect(Unit) {
    consentManager.requestConsentAndShowFormIfNeeded(activity)
  }
}
