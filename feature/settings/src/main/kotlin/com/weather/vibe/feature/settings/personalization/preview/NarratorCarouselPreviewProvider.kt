package com.weather.vibe.feature.settings.personalization.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonaUiState
import kotlinx.collections.immutable.ImmutableList

internal class NarratorCarouselPreviewProvider :
  PreviewParameterProvider<ImmutableList<PersonaUiState>> {

  override val values: Sequence<ImmutableList<PersonaUiState>> =
    sequenceOf(PersonalizationPreviewData.personas)
}
