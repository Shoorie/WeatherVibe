package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.usecase.GetPollen
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.weather.model.Coordinates
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.annotation.Factory

@Factory
class GatherPollenAlerts internal constructor(
  private val alertDeduplicator: AlertDeduplicator,
  private val detectPollenAlert: DetectPollenAlert,
  private val getPollen: GetPollen,
  private val observeCurrentLocation: ObserveCurrentLocation
) {

  suspend operator fun invoke(): List<WeatherAlert> {
    val coordinates = currentCoordinates() ?: return emptyList()
    val pollen = getPollen(coordinates).getOrNull() ?: return emptyList()
    val alert = detectPollenAlert(pollen) ?: return emptyList()
    return alertDeduplicator.filterFresh(listOf(alert))
  }

  private suspend fun currentCoordinates(): Coordinates? =
    observeCurrentLocation().firstOrNull()?.toCoordinates()
}
