package com.weather.vibe.feature.settings.personalization.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loading
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreviewData.loaded
import kotlinx.collections.immutable.persistentListOf

internal class PersonalizationPreview :
  PreviewParameterProvider<PersonalizationUiState> {

  private val premium: PersonalizationUiState =
    loaded.copy(
      genreChips = persistentListOf(),
      hasExcludedGenres = false,
      isPremium = true
    )

  private val error: PersonalizationUiState =
    Error(message = "Failed to load personalization")

  override val values: Sequence<PersonalizationUiState> =
    sequenceOf(loaded, premium, Loading, error)
}
