package com.weather.vibe.domain.ads.consent

import kotlinx.coroutines.flow.Flow

interface AdConsentState {
  val canRequestAds: Flow<Boolean>
}
