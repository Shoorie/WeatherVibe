package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreviewData.afternoonSunInfo
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.eightHoursForecast
import com.weather.vibe.feature.home.preview.HomePreviewData.header
import com.weather.vibe.feature.home.preview.HomePreviewData.highPollenChip
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist
import com.weather.vibe.feature.home.preview.HomePreviewData.moderateAirQualityChip
import com.weather.vibe.feature.home.preview.HomePreviewData.smogAlert
import com.weather.vibe.feature.home.preview.HomePreviewData.warmDayCurrent
import com.weather.vibe.feature.home.preview.HomePreviewData.weekForecast

internal class HomePreview :
  PreviewParameterProvider<HomeUiState> {

  private val loadingState: HomeUiState = Loading

  private val errorState: HomeUiState =
    Error("Network connection problem.")

  private val successWithForecast: HomeUiState =
    Loaded(
      currentWeather = warmDayCurrent,
      dailyForecast = weekForecast,
      detailsSections = detailsSections,
      header = header,
      hourlyForecast = eightHoursForecast,
      sunriseSunset = afternoonSunInfo
    )

  private val successWithAiContent: HomeUiState =
    Loaded(
      airQualityChip = moderateAirQualityChip,
      alert = smogAlert,
      briefing = BriefingUiState.Loaded(
        text = "A mild partly cloudy day with a light breeze — " +
          "great for a walk before the evening rain."
      ),
      currentWeather = warmDayCurrent,
      dailyForecast = weekForecast,
      detailsSections = detailsSections,
      header = header,
      hourlyForecast = eightHoursForecast,
      playlist = loadedPlaylist,
      pollenChip = highPollenChip,
      sunriseSunset = afternoonSunInfo
    )

  override val values: Sequence<HomeUiState> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast,
      successWithAiContent
    )
}
