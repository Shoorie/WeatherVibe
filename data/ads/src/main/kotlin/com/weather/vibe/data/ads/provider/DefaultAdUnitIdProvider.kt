package com.weather.vibe.data.ads.provider

import com.weather.vibe.data.ads.resources.AdUnitResources
import com.weather.vibe.domain.ads.placement.AdPlacement
import com.weather.vibe.domain.ads.provider.AdUnitIdProvider
import org.koin.core.annotation.Single

@Single(binds = [AdUnitIdProvider::class])
internal class DefaultAdUnitIdProvider(
  private val resources: AdUnitResources
) : AdUnitIdProvider {

  override fun idFor(placement: AdPlacement): String =
    resources.idFor(placement)
}
