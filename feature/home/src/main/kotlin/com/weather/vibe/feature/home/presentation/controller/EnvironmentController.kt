package com.weather.vibe.feature.home.presentation.controller

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.airquality.usecase.GetEnvironmentalReadings
import com.weather.vibe.domain.alerts.usecase.ResolveHomeAlert
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.feature.home.presentation.factory.EnvironmentSectionFactory
import com.weather.vibe.feature.home.presentation.state.EnvironmentSectionUiState
import org.koin.core.annotation.Factory

@Factory
internal class EnvironmentController(
  private val getEnvironmentalReadings: GetEnvironmentalReadings,
  private val resolveHomeAlert: ResolveHomeAlert,
  private val sectionFactory: EnvironmentSectionFactory
) {

  suspend fun fetchReadings(coordinates: Coordinates): EnvironmentalReadings =
    getEnvironmentalReadings(coordinates)

  fun buildSection(
    alertsEnabled: Boolean,
    readings: EnvironmentalReadings
  ): EnvironmentSectionUiState {
    val alert = resolveHomeAlert(readings = readings, alertsEnabled = alertsEnabled)
    return sectionFactory.create(readings = readings, alert = alert)
  }
}
