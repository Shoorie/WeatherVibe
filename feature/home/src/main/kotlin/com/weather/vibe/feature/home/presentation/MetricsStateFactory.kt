package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.feature.home.ui.HomeResources.Emojis
import org.koin.core.annotation.Factory
import java.util.Locale
import kotlin.math.roundToInt

@Factory
internal class MetricsStateFactory(private val resources: HomeResources) {

  fun create(data: WeatherData): MetricsUiState {

    val today = data.dailyForecast.firstOrNull()
    val precipitation = data.hourlyForecast.firstOrNull()
      ?.precipitationProbability

    return MetricsUiState(
      items = listOf(
        humidity(data.humidity),
        windSpeed(data.windSpeed),
        windDirection(data.windDirection),
        precipitation(precipitation ?: 0),
        uvIndex(today?.uvIndexMax ?: 0.0),
        cloudCover(data.cloudCover),
        pressure(data.surfacePressure),
        visibility(data.visibility),
        dewPoint(data.dewPoint),
        windGusts(data.windGusts),
        windSpeedMax(today?.windSpeedMax ?: 0.0),
        rainfall(today?.precipitationSum ?: 0.0)
      )
    )
  }

  private fun humidity(value: Int): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.humidity(),
      label = resources.humidity(),
      value = formatPercent(value)
    )

  private fun windSpeed(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.wind(),
      label = resources.windSpeed(),
      value = formatSpeed(value)
    )

  private fun windDirection(degrees: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.compass(),
      label = resources.direction(),
      value = formatDirection(degrees)
    )

  private fun precipitation(probability: Int): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.precipitation(),
      label = resources.precipitation(),
      value = formatPercent(probability)
    )

  private fun uvIndex(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.uvIndex(),
      label = resources.uvIndex(),
      value = String.format(Locale.US, UV_INDEX_FORMAT, value)
    )

  private fun cloudCover(value: Int): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.cloud(),
      label = resources.cloudCover(),
      value = formatPercent(value)
    )

  private fun pressure(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.gauge(),
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
      icon = Emojis.eye(),
      label = resources.visibility(),
      value = formatted
    )
  }

  private fun dewPoint(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.dewDrop(),
      label = resources.dewPoint(),
      value = "${value.roundToInt()}$DEGREE_SYMBOL"
    )

  private fun windGusts(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.windGusts(),
      label = resources.windGusts(),
      value = formatSpeed(value)
    )

  private fun windSpeedMax(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.windMax(),
      label = resources.windSpeedMax(),
      value = formatSpeed(value)
    )

  private fun rainfall(value: Double): MetricItemUiState =
    MetricItemUiState(
      icon = Emojis.rainfall(),
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
