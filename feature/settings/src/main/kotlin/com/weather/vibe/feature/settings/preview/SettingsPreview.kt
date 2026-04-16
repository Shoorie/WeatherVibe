package com.weather.vibe.feature.settings.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Error
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.briefToneOptions
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.genreChips

internal class SettingsPreview :
  PreviewParameterProvider<SettingsUiState> {

  private val loading: SettingsUiState = Loading

  private val loaded: SettingsUiState =
    Loaded(
      alertsEnabled = true,
      briefToneOptions = briefToneOptions,
      genreChips = genreChips,
      hasExcludedGenres = true,
      isCelsius = true,
      morningBriefEnabled = true
    )

  private val loadedNoExcludedGenres: SettingsUiState =
    Loaded(
      alertsEnabled = false,
      briefToneOptions = briefToneOptions,
      genreChips = emptyList(),
      hasExcludedGenres = false,
      isCelsius = false,
      morningBriefEnabled = false
    )

  private val error: SettingsUiState =
    Error(message = "Unable to load settings.")

  override val values: Sequence<SettingsUiState> =
    sequenceOf(loading, loaded, loadedNoExcludedGenres, error)
}
