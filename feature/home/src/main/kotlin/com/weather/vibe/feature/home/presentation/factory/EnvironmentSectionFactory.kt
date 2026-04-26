package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.FAIR
import com.weather.vibe.domain.airquality.model.AqiLevel.GOOD
import com.weather.vibe.domain.airquality.model.AqiLevel.MODERATE
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.VERY_POOR
import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.airquality.model.PollenReading
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.alerts.model.WeatherAlert.HighPollen
import com.weather.vibe.domain.alerts.model.WeatherAlert.PoorAirQuality
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.EnvChipTint
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.AMBER
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.GREEN
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.ROSE
import com.weather.vibe.feature.home.presentation.state.HomeAlertUiState
import com.weather.vibe.feature.home.presentation.state.PollenChipUiState
import com.weather.vibe.feature.home.ui.HomeAirQualityResources
import org.koin.core.annotation.Factory

@Factory
internal class EnvironmentSectionFactory(
  private val resources: HomeAirQualityResources
) {

  fun buildAirQualityChip(readings: EnvironmentalReadings): AirQualityChipUiState? =
    readings.airQuality?.let(::createAirQualityChip)

  fun buildPollenChip(readings: EnvironmentalReadings): PollenChipUiState? =
    readings.pollen?.notableReading?.let(::createPollenChip)

  fun buildAlert(alert: WeatherAlert?): HomeAlertUiState? =
    alert?.let(::createAlert)

  private fun createAirQualityChip(airQuality: AirQuality): AirQualityChipUiState =
    AirQualityChipUiState(
      indicator = resources.airQualityIndicator(airQuality.level),
      label = resources.airQualityLabel(airQuality.level),
      contentDescription = resources.airQualityChipContentDescription(
        level = airQuality.level,
        europeanAqi = airQuality.europeanAqi
      ),
      tint = airQuality.level.toChipTint()
    )

  private fun createPollenChip(reading: PollenReading): PollenChipUiState =
    PollenChipUiState(
      indicator = resources.pollenIndicator(),
      label = resources.pollenLabel(reading.level),
      contentDescription = resources.pollenChipContentDescription(
        level = reading.level,
        species = reading.species
      ),
      tint = GREEN
    )

  private fun AqiLevel.toChipTint(): EnvChipTint = when (this) {
    GOOD -> GREEN
    FAIR, MODERATE -> AMBER
    POOR, VERY_POOR, EXTREMELY_POOR -> ROSE
  }

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
