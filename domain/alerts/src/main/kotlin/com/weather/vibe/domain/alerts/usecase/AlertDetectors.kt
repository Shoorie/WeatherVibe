package com.weather.vibe.domain.alerts.usecase

import org.koin.core.annotation.Factory

@Factory
internal data class AlertDetectors(
  val detectAqiAlert: DetectAqiAlert,
  val detectPollenAlert: DetectPollenAlert,
  val detectWeatherAlerts: DetectWeatherAlerts
)
