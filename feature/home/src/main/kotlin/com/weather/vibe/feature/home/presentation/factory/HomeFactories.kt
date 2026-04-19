package com.weather.vibe.feature.home.presentation.factory

import org.koin.core.annotation.Factory

@Factory
internal data class HomeFactories(
  val aiSuggestion: AiSuggestionSectionFactory,
  val environment: EnvironmentSectionFactory,
  val forecast: ForecastSectionFactory,
  val metrics: MetricsStateFactory,
  val sharePoster: SharePosterFactory
)
