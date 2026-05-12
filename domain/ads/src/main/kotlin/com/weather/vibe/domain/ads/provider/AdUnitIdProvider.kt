package com.weather.vibe.domain.ads.provider

import com.weather.vibe.domain.ads.placement.AdPlacement

interface AdUnitIdProvider {
  fun idFor(placement: AdPlacement): String
}
