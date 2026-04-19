package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.WeatherMetrics
import com.weather.vibe.domain.weather.model.WindDirection
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeEmojis
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.util.Locale
import kotlin.math.roundToInt

@Factory
internal class MetricsStateFactory(
  private val resources: HomeResources,
  private val temperature: TemperatureFormatter
) {

  fun create(
    metrics: WeatherMetrics,
    temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS
  ): DetailsSectionsUiState {

    val windItems = listOf(
      windSpeed(metrics.windSpeed),
      windDirection(metrics.windDirection),
      windGusts(metrics.windGusts),
      windSpeedMax(metrics.windSpeedMax)
    )

    val atmosphereItems = listOf(
      humidity(metrics.humidity),
      pressure(metrics.surfacePressure),
      dewPoint(metrics.dewPoint, temperatureUnit),
      cloudCover(metrics.cloudCover)
    )

    val conditionsItems = listOf(
      precipitation(metrics.precipitationProbability),
      uvIndex(metrics.uvIndexMax),
      visibility(metrics.visibility),
      rainfall(metrics.precipitationSum)
    )

    return DetailsSectionsUiState(
      atmosphere = atmosphereItems.toImmutableList(),
      conditions = conditionsItems.toImmutableList(),
      previewItems = persistentListOf(
        atmosphereItems[PREVIEW_HUMIDITY_INDEX],
        windItems[PREVIEW_WIND_INDEX],
        conditionsItems[PREVIEW_UV_INDEX],
        conditionsItems[PREVIEW_PRECIPITATION_INDEX]
      ),
      wind = windItems.toImmutableList()
    )
  }

  private fun humidity(value: Int): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.humidity(),
      label = resources.humidity(),
      value = formatPercent(value)
    )

  private fun windSpeed(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.wind(),
      label = resources.windSpeed(),
      value = formatSpeed(value)
    )

  private fun windDirection(direction: WindDirection): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.compass(),
      label = resources.direction(),
      value = direction.name
    )

  private fun precipitation(probability: Int): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.precipitation(),
      label = resources.precipitation(),
      value = formatPercent(probability)
    )

  private fun uvIndex(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.uvIndex(),
      label = resources.uvIndex(),
      value = String.format(Locale.US, UV_INDEX_FORMAT, value)
    )

  private fun cloudCover(value: Int): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.cloud(),
      label = resources.cloudCover(),
      value = formatPercent(value)
    )

  private fun pressure(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.gauge(),
      label = resources.pressure(),
      value = "${value.roundToInt()} $PRESSURE_UNIT"
    )

  private fun visibility(meters: Double): MetricItemUiState {
    val km = meters / METERS_PER_KM
    val formatted = when {
      km >= VISIBILITY_KM_THRESHOLD -> "${km.roundToInt()} $VISIBILITY_UNIT_KM"
      else -> "${meters.roundToInt()} $VISIBILITY_UNIT_M"
    }
    return MetricItemUiState(
      icon = HomeEmojis.eye(),
      label = resources.visibility(),
      value = formatted
    )
  }

  private fun dewPoint(value: Double, unit: TemperatureUnit): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.dewDrop(),
      label = resources.dewPoint(),
      value = temperature.format(celsius = value, unit = unit)
    )

  private fun windGusts(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.windGusts(),
      label = resources.windGusts(),
      value = formatSpeed(value)
    )

  private fun windSpeedMax(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.windMax(),
      label = resources.windSpeedMax(),
      value = formatSpeed(value)
    )

  private fun rainfall(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = HomeEmojis.rainfall(),
      label = resources.rainfall(),
      value = String.format(Locale.getDefault(), MILLIMETERS_FORMAT, value)
    )

  private fun formatPercent(value: Int): String =
    "$value$PERCENT_SYMBOL"

  private fun formatSpeed(value: Double): String =
    "${value.roundToInt()} $SPEED_UNIT"

  private companion object {

    const val METERS_PER_KM = 1000.0
    const val MILLIMETERS_FORMAT = "%.1f mm"
    const val PERCENT_SYMBOL = "%"
    const val PRESSURE_UNIT = "hPa"
    const val SPEED_UNIT = "km/h"
    const val UV_INDEX_FORMAT = "%.1f"
    const val VISIBILITY_KM_THRESHOLD = 1.0
    const val VISIBILITY_UNIT_KM = "km"
    const val VISIBILITY_UNIT_M = "m"

    const val PREVIEW_HUMIDITY_INDEX = 0
    const val PREVIEW_PRECIPITATION_INDEX = 0
    const val PREVIEW_UV_INDEX = 1
    const val PREVIEW_WIND_INDEX = 0
  }
}
