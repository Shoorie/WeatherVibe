package com.weather.vibe.core.ads.data

import com.weather.vibe.core.ads.domain.AdPlacement

interface AdUnitIdProvider {

  fun idFor(placement: AdPlacement): String
}
