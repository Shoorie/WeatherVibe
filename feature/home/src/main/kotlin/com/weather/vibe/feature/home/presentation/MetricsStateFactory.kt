package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.compass
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.eye
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.gauge
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.precipitation
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windGusts
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windMax
import org.koin.core.annotation.Factory
import java.util.Locale
import kotlin.math.roundToInt

@Factory
internal class MetricsStateFactory(private val resources: HomeResources) {

  fun create(data: WeatherData): DetailsSectionsUiState {

    val today = data.dailyForecast.firstOrNull()
    val precipitationProb = data.hourlyForecast.firstOrNull()
      ?.precipitationProbability

    val windItems = listOf(
      windSpeed(data.windSpeed),
      windDirection(data.windDirection),
      windGusts(data.windGusts),
      windSpeedMax(today?.windSpeedMax ?: 0.0)
    )

    val atmosphereItems = listOf(
      humidity(data.humidity),
      pressure(data.surfacePressure),
      dewPoint(data.dewPoint),
      cloudCover(data.cloudCover)
    )

    val conditionsItems = listOf(
      precipitation(precipitationProb ?: 0),
      uvIndex(today?.uvIndexMax ?: 0.0),
      visibility(data.visibility),
      rainfall(today?.precipitationSum ?: 0.0)
    )

    return DetailsSectionsUiState(
      atmosphere = atmosphereItems,
      conditions = conditionsItems,
      previewItems = listOf(
        atmosphereItems[0],
        windItems[0],
        conditionsItems[1],
        conditionsItems[0]
      ),
      wind = windItems
    )
  }

  private fun humidity(value: Int): MetricItemUiState =
    MetricItemUiState(
      icon = humidity(),
      label = resources.humidity(),
      value = formatPercent(value)
    )

  private fun windSpeed(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = wind(),
      label = resources.windSpeed(),
      value = formatSpeed(value)
    )

  private fun windDirection(degrees: Double): MetricItemUiState =
    MetricItemUiState(
      icon = compass(),
      label = resources.direction(),
      value = formatDirection(degrees)
    )

  private fun precipitation(probability: Int): MetricItemUiState =
    MetricItemUiState(
      icon = precipitation(),
      label = resources.precipitation(),
      value = formatPercent(probability)
    )

  private fun uvIndex(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = uvIndex(),
      label = resources.uvIndex(),
      value = String.format(Locale.US, UV_INDEX_FORMAT, value)
    )

  private fun cloudCover(value: Int): MetricItemUiState =
    MetricItemUiState(
      icon = cloud(),
      label = resources.cloudCover(),
      value = formatPercent(value)
    )

  private fun pressure(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = gauge(),
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
      icon = eye(),
      label = resources.visibility(),
      value = formatted
    )
  }

  private fun dewPoint(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = dewDrop(),
      label = resources.dewPoint(),
      value = "${value.roundToInt()}$DEGREE_SYMBOL"
    )

  private fun windGusts(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = windGusts(),
      label = resources.windGusts(),
      value = formatSpeed(value)
    )

  private fun windSpeedMax(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = windMax(),
      label = resources.windSpeedMax(),
      value = formatSpeed(value)
    )

  private fun rainfall(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = rainfall(),
      label = resources.rainfall(),
      value = String.format(Locale.US, MILLIMETERS_FORMAT, value)
    )

  private fun formatDirection(degrees: Double): String {
    val index = ((degrees / DIRECTION_STEP) + DIRECTION_OFFSET)
      .toInt() % WIND_DIRECTIONS.size
    return WIND_DIRECTIONS[index]
  }

  private fun formatPercent(value: Int): String =
    "$value$PERCENT_SYMBOL"

  private fun formatSpeed(value: Double): String =
    "${value.roundToInt()} $SPEED_UNIT"

  private companion object {
    const val DEGREE_SYMBOL = "°"
    const val DIRECTION_OFFSET = 0.5
    const val DIRECTION_STEP = 45.0
    const val METERS_PER_KM = 1000.0
    const val MILLIMETERS_FORMAT = "%.1f mm"
    const val PERCENT_SYMBOL = "%"
    const val PRESSURE_UNIT = "hPa"
    const val SPEED_UNIT = "km/h"
    const val UV_INDEX_FORMAT = "%.1f"
    const val VISIBILITY_KM_THRESHOLD = 1.0
    const val VISIBILITY_UNIT_KM = "km"
    const val VISIBILITY_UNIT_M = "m"
    val WIND_DIRECTIONS = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
  }
}
