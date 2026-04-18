package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.airquality.model.PollenReading
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.alerts.model.WeatherAlert.HighPollen
import com.weather.vibe.domain.alerts.model.WeatherAlert.PoorAirQuality
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.AirQualityPresentation
import com.weather.vibe.feature.home.presentation.state.HomeAlertUiState
import com.weather.vibe.feature.home.presentation.state.PollenChipUiState
import com.weather.vibe.feature.home.ui.HomeAirQualityResources
import org.koin.core.annotation.Factory

@Factory
internal class AirQualityStateFactory(
  private val resources: HomeAirQualityResources
) {

  fun createPresentation(
    readings: EnvironmentalReadings,
    alert: WeatherAlert?
  ): AirQualityPresentation =
    AirQualityPresentation(
      airQualityChip = readings.airQuality?.let(::createAirQualityChip),
      pollenChip = readings.pollen?.notableReading?.let(::createPollenChip),
      alert = alert?.let(::createAlert)
    )

  private fun createAirQualityChip(
    airQuality: AirQuality
  ): AirQualityChipUiState =
    AirQualityChipUiState(
      indicator = resources.airQualityIndicator(airQuality.level),
      label = resources.airQualityLabel(airQuality.level),
      contentDescription = resources.airQualityChipContentDescription(
        level = airQuality.level,
        europeanAqi = airQuality.europeanAqi
      )
    )

  private fun createPollenChip(reading: PollenReading): PollenChipUiState =
    PollenChipUiState(
      indicator = resources.pollenIndicator(),
      label = resources.pollenLabel(reading.level),
      contentDescription = resources.pollenChipContentDescription(
        level = reading.level,
        species = reading.species
      )
    )

  private fun createAlert(alert: WeatherAlert): HomeAlertUiState? =
    when (alert) {
      is PoorAirQuality -> createAqiAlert(alert)
      is HighPollen -> createPollenAlert(alert)
      else -> null
    }

  private fun createAqiAlert(alert: PoorAirQuality): HomeAlertUiState =
    HomeAlertUiState(
      indicator = resources.alertIndicator(),
      title = resources.aqiAlertTitle(),
      message = resources.aqiAlertMessage(alert.level),
      contentDescription = resources.aqiAlertContentDescription(
        level = alert.level,
        europeanAqi = alert.europeanAqi
      )
    )

  private fun createPollenAlert(alert: HighPollen): HomeAlertUiState {
    val speciesList = resources.joinSpecies(alert.species)
    return HomeAlertUiState(
      indicator = resources.alertIndicator(),
      title = resources.pollenAlertTitle(),
      message = resources.pollenAlertMessage(speciesList),
      contentDescription = resources.pollenAlertContentDescription(speciesList)
    )
  }
}
