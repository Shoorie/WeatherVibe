package com.weather.vibe.feature.home.ui

import android.content.Context
import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.FAIR
import com.weather.vibe.domain.airquality.model.AqiLevel.GOOD
import com.weather.vibe.domain.airquality.model.AqiLevel.MODERATE
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.VERY_POOR
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.airquality.model.PollenSpecies
import com.weather.vibe.feature.home.R
import org.koin.core.annotation.Factory

@Factory
internal class HomeAirQualityResources(private val context: Context) {

  fun airQualityIndicator(level: AqiLevel): String = when (level) {
    GOOD -> Emojis.aqiGood()
    FAIR -> Emojis.aqiFair()
    MODERATE -> Emojis.aqiModerate()
    POOR -> Emojis.aqiPoor()
    VERY_POOR -> Emojis.aqiVeryPoor()
    EXTREMELY_POOR -> Emojis.aqiExtremelyPoor()
  }

  fun airQualityLabel(level: AqiLevel): String =
    context.getString(level.labelRes())

  fun airQualityChipContentDescription(
    level: AqiLevel,
    europeanAqi: Int
  ): String =
    context.getString(
      R.string.home_aqi_chip_content_description_format,
      airQualityLabel(level),
      europeanAqi
    )

  fun pollenIndicator(): String =
    Emojis.pollen()

  fun pollenLabel(level: PollenLevel): String =
    context.getString(level.labelRes())

  fun pollenChipContentDescription(
    level: PollenLevel,
    species: PollenSpecies
  ): String =
    context.getString(
      R.string.home_pollen_chip_content_description_format,
      pollenLabel(level),
      speciesLabel(species)
    )

  fun alertIndicator(): String =
    Emojis.warning()

  fun aqiAlertTitle(): String =
    context.getString(R.string.home_aqi_alert_title)

  fun aqiAlertMessage(level: AqiLevel): String =
    context.getString(
      R.string.home_aqi_alert_message_format,
      airQualityLabel(level)
    )

  fun aqiAlertContentDescription(
    level: AqiLevel,
    europeanAqi: Int
  ): String =
    context.getString(
      R.string.home_aqi_alert_content_description_format,
      airQualityLabel(level),
      europeanAqi
    )

  fun pollenAlertTitle(): String =
    context.getString(R.string.home_pollen_alert_title)

  fun pollenAlertMessage(speciesList: String): String =
    context.getString(R.string.home_pollen_alert_message_format, speciesList)

  fun pollenAlertContentDescription(speciesList: String): String =
    context.getString(
      R.string.home_pollen_alert_content_description_format,
      speciesList
    )

  fun speciesLabel(species: PollenSpecies): String =
    context.getString(species.labelRes())

  fun joinSpecies(species: List<PollenSpecies>): String =
    species.joinToString(separator = ", ", transform = ::speciesLabel)

  private fun AqiLevel.labelRes(): Int = when (this) {
    GOOD -> R.string.home_aqi_level_good
    FAIR -> R.string.home_aqi_level_fair
    MODERATE -> R.string.home_aqi_level_moderate
    POOR -> R.string.home_aqi_level_poor
    VERY_POOR -> R.string.home_aqi_level_very_poor
    EXTREMELY_POOR -> R.string.home_aqi_level_extremely_poor
  }

  private fun PollenLevel.labelRes(): Int = when (this) {
    PollenLevel.LOW -> R.string.home_pollen_level_low
    PollenLevel.MODERATE -> R.string.home_pollen_level_moderate
    PollenLevel.HIGH -> R.string.home_pollen_level_high
    PollenLevel.VERY_HIGH -> R.string.home_pollen_level_very_high
  }

  private fun PollenSpecies.labelRes(): Int = when (this) {
    PollenSpecies.ALDER -> R.string.home_pollen_species_alder
    PollenSpecies.BIRCH -> R.string.home_pollen_species_birch
    PollenSpecies.GRASS -> R.string.home_pollen_species_grass
    PollenSpecies.MUGWORT -> R.string.home_pollen_species_mugwort
    PollenSpecies.OLIVE -> R.string.home_pollen_species_olive
    PollenSpecies.RAGWEED -> R.string.home_pollen_species_ragweed
  }

  object Emojis {
    fun aqiGood(): String = "\uD83D\uDFE2"
    fun aqiFair(): String = "\uD83D\uDFE1"
    fun aqiModerate(): String = "\uD83D\uDFE0"
    fun aqiPoor(): String = "\uD83D\uDD34"
    fun aqiVeryPoor(): String = "\uD83D\uDFE3"
    fun aqiExtremelyPoor(): String = "\u26AB"
    fun pollen(): String = "\uD83C\uDF3F"
    fun warning(): String = "\u26A0\uFE0F"
  }
}
