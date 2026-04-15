package com.weather.vibe.feature.home.presentation.factory

import org.koin.core.annotation.Factory

@Factory
internal data class HomeFactories(
  val metrics: MetricsStateFactory,
  val playlist: PlaylistStateFactory,
  val sunriseSunset: SunriseSunsetStateFactory
)
