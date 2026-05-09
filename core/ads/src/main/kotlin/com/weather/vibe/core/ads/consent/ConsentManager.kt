package com.weather.vibe.core.ads.consent

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface ConsentManager {

  val canRequestAds: Flow<Boolean>

  suspend fun requestConsentAndShowFormIfNeeded(activity: Activity)
}
