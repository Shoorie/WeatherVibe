package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.mostlySunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunShower

internal class DailyForecastListPreview :
  PreviewParameterProvider<List<DailyForecastUiState>> {

  private val weekForecast: List<DailyForecastUiState> =
    listOf(
      DailyForecastUiState(partlyCloudy(), "Today", "22°", "14°"),
      DailyForecastUiState(rainfall(), "Tue", "19°", "11°"),
      DailyForecastUiState(cloud(), "Wed", "15°", "8°"),
      DailyForecastUiState(sunny(), "Thu", "24°", "16°"),
      DailyForecastUiState(mostlySunny(), "Fri", "21°", "13°"),
      DailyForecastUiState(sunShower(), "Sat", "17°", "10°"),
      DailyForecastUiState(partlyCloudy(), "Sun", "20°", "12°")
    )

  override val values: Sequence<List<DailyForecastUiState>> =
    sequenceOf(weekForecast)
}
