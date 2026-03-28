package com.weather.vibe.feature.settings.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Error
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading

internal class SettingsPreview :
  PreviewParameterProvider<SettingsUiState> {

  private val loading: SettingsUiState = Loading

  private val loaded: SettingsUiState =
    Loaded(
      excludedGenres = "jazz",
      isCelsius = true,
      personaOptions = SettingsPreviewData.personaOptions
    )

  private val error: SettingsUiState =
    Error(message = "Unable to load settings.")

  override val values: Sequence<SettingsUiState> =
    sequenceOf(loading, loaded, error)
}
