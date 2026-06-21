package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.feature.home.presentation.state.BriefingPersonaUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreviewData.aiSuggestionSection
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.forecastSection
import com.weather.vibe.feature.home.preview.HomePreviewData.pleasantDailyVibeCard
import com.weather.vibe.feature.home.preview.HomePreviewData.smogAlert

internal class HomePreview :
  PreviewParameterProvider<HomeUiState> {

  private val loadingState: HomeUiState = Loading

  private val errorState: HomeUiState =
    Error("Network connection problem.")

  private val successWithForecast: HomeUiState =
    Loaded(
      details = detailsSections,
      forecast = forecastSection
    )

  private val successWithAiContent: HomeUiState =
    Loaded(
      aiSuggestion = aiSuggestionSection.copy(
        briefing = BriefingUiState.Loaded(
          persona = BriefingPersonaUiState(
            colorKey = PersonaColorKey.WITTY_AND_FRIENDLY,
            emoji = "😊"
          ),
          text = "A mild partly cloudy day with a light breeze — " +
            "great for a walk before the evening rain."
        )
      ),
      alert = smogAlert,
      dailyVibe = pleasantDailyVibeCard,
      details = detailsSections,
      forecast = forecastSection
    )

  override val values: Sequence<HomeUiState> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast,
      successWithAiContent
    )
}
