package com.weather.vibe.feature.settings.personalization.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreviewData.briefToneOptions
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreviewData.genreChips
import kotlinx.collections.immutable.persistentListOf

internal class PersonalizationPreview :
  PreviewParameterProvider<PersonalizationUiState> {

  private val loaded: PersonalizationUiState =
    Loaded(
      briefToneOptions = briefToneOptions,
      genreChips = genreChips,
      hasExcludedGenres = true,
      isCelsius = true
    )

  private val loadedNoGenres: PersonalizationUiState =
    Loaded(
      briefToneOptions = briefToneOptions,
      genreChips = persistentListOf(),
      hasExcludedGenres = false,
      isCelsius = false
    )

  private val error: PersonalizationUiState =
    Error(message = "Failed to load personalization")

  override val values: Sequence<PersonalizationUiState> =
    sequenceOf(loaded, loadedNoGenres, error)
}
