package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.feature.home.presentation.state.BriefingPersonaUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Limit
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading

internal class WeatherBriefingCardPreview :
  PreviewParameterProvider<BriefingUiState> {

  private val persona =
    BriefingPersonaUiState(colorKey = PersonaColorKey.COACH, emoji = "🏋️")

  private val sample =
    "Expect a mild and partly cloudy day with a light breeze — " +
      "a good day for a walk before the evening rain arrives."

  private val loading: BriefingUiState = Loading

  private val loaded: BriefingUiState =
    Loaded(
      persona = persona,
      text = sample,
      outfit = "Light jacket, sneakers, umbrella"
    )

  private val limit: BriefingUiState =
    Limit(
      persona = persona,
      teaser = sample,
      outfit = "Light jacket, sneakers, umbrella"
    )

  private val error: BriefingUiState = Error(canRetry = true)

  override val values: Sequence<BriefingUiState> =
    sequenceOf(loading, loaded, limit, error)
}
