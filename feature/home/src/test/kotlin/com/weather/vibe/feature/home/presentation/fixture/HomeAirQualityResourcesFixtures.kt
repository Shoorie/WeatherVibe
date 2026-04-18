package com.weather.vibe.feature.home.presentation.fixture

import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.airquality.model.PollenSpecies

internal object HomeAirQualityResourcesFixtures {

  const val POLLEN_INDICATOR = "pollen-indicator"
  const val ALERT_INDICATOR = "alert-indicator"
  const val AQI_ALERT_TITLE = "aqi-alert-title"
  const val POLLEN_ALERT_TITLE = "pollen-alert-title"

  fun aqiIndicator(level: AqiLevel): String =
    "aqi-indicator-${level.name}"

  fun aqiLabel(level: AqiLevel): String =
    "aqi-label-${level.name}"

  fun aqiChipDescription(level: AqiLevel, europeanAqi: Int): String =
    "aqi-cd-${level.name}-$europeanAqi"

  fun pollenLabel(level: PollenLevel): String =
    "pollen-label-${level.name}"

  fun pollenChipDescription(
    level: PollenLevel,
    species: PollenSpecies
  ): String =
    "pollen-cd-${level.name}-${species.name}"

  fun speciesLabel(species: PollenSpecies): String =
    "species-${species.name}"

  fun speciesJoin(species: List<PollenSpecies>): String =
    species.joinToString(separator = ", ", transform = ::speciesLabel)

  fun aqiAlertMessage(level: AqiLevel): String =
    "aqi-alert-msg-${level.name}"

  fun aqiAlertDescription(level: AqiLevel, europeanAqi: Int): String =
    "aqi-alert-cd-${level.name}-$europeanAqi"

  fun pollenAlertMessage(speciesList: String): String =
    "pollen-alert-msg-$speciesList"

  fun pollenAlertDescription(speciesList: String): String =
    "pollen-alert-cd-$speciesList"
}
