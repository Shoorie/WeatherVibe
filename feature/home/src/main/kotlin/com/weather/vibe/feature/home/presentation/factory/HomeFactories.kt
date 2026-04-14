package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.feature.home.presentation.factory.MetricsStateFactory
import com.weather.vibe.feature.home.presentation.factory.PlaylistStateFactory
import com.weather.vibe.feature.home.presentation.factory.SunriseSunsetStateFactory
import org.koin.core.annotation.Factory

@Factory
internal data class HomeFactories(
  val metrics: MetricsStateFactory,
  val playlist: PlaylistStateFactory,
  val sunriseSunset: SunriseSunsetStateFactory
)
