package com.weather.vibe.data.ads.consent

import android.app.Activity
import com.weather.vibe.domain.ads.consent.AdConsentState

interface ConsentManager : AdConsentState {
  suspend fun requestConsentAndShowFormIfNeeded(activity: Activity)
}
