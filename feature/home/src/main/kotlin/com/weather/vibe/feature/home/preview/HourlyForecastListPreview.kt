package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.mostlySunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny

internal class HourlyForecastListPreview :
  PreviewParameterProvider<HourlyForecastsUiState> {

  private val conditionEmojis: List<String> =
    listOf(
      partlyCloudy(),
      cloud(),
      mostlySunny(),
      sunny(),
      partlyCloudy(),
      rainfall(),
      cloud(),
      mostlySunny()
    )

  private val eightHours = HourlyForecastsUiState(
    items = List(8) { index ->
      HourlyForecastUiState(
        conditionEmoji = conditionEmojis[index],
        isCurrentHour = index == 0,
        temperature = "${18 + index}°",
        timeLabel = "${14 + index}:00"
      )
    }
  )

  override val values: Sequence<HourlyForecastsUiState> =
    sequenceOf(eightHours)
}
