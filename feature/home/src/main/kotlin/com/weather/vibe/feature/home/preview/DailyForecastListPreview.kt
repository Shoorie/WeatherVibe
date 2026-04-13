package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.mostlySunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunShower
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny

internal class DailyForecastListPreview :
  PreviewParameterProvider<DailyForecastsUiState> {

  private val weekForecast = DailyForecastsUiState(
    items = listOf(
      day("Today", partlyCloudy(), "Partly Cloudy", "22°", "14°", 0.7f, 0.95f, 0.85f, true),
      day("Tue", rainfall(), "Rain", "19°", "11°", 0.55f, 0.78f),
      day("Wed", cloud(), "Cloudy", "15°", "8°", 0.4f, 0.55f),
      day("Thu", sunny(), "Sunny", "24°", "16°", 0.78f, 1f),
      day("Fri", mostlySunny(), "Mostly Sunny", "21°", "13°", 0.62f, 0.85f),
      day("Sat", sunShower(), "Showers", "17°", "10°", 0.48f, 0.65f),
      day("Sun", partlyCloudy(), "Partly Cloudy", "20°", "12°", 0.58f, 0.8f)
    )
  )

  override val values: Sequence<DailyForecastsUiState> =
    sequenceOf(weekForecast)

  @Suppress("LongParameterList")
  private fun day(
    label: String,
    emoji: String,
    condition: String,
    max: String,
    min: String,
    rangeStart: Float,
    rangeEnd: Float,
    current: Float? = null,
    isToday: Boolean = false
  ): DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = emoji,
      conditionLabel = condition,
      dayLabel = label,
      isToday = isToday,
      maxTemperature = max,
      minTemperature = min,
      range = DailyRangeUiState(
        startFraction = rangeStart,
        endFraction = rangeEnd,
        currentFraction = current
      )
    )
}
