package com.weather.vibe.feature.locations.presentation.factory

import org.koin.core.annotation.Factory

@Factory
internal data class LocationsFactories(
  val card: LocationCardFactory,
  val compare: LocationCompareFactory,
  val loaded: LocationsLoadedFactory,
  val state: LocationsStateFactory
)
