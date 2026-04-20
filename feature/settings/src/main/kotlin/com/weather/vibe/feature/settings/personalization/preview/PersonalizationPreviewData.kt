package com.weather.vibe.feature.settings.personalization.preview

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.settings.personalization.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.GenreChipUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object PersonalizationPreviewData {

  val briefToneOptions: ImmutableList<BriefToneOptionUiState> =
    persistentListOf(
      BriefToneOptionUiState(
        description = "Light, warm, and conversational",
        isSelected = true,
        label = "Witty & Friendly",
        tone = WITTY_AND_FRIENDLY
      ),
      BriefToneOptionUiState(
        description = "Clear, concise, and business-like",
        isSelected = false,
        label = "Formal & Professional",
        tone = FORMAL
      ),
      BriefToneOptionUiState(
        description = "Light irony, dry wit, and gentle sarcasm",
        isSelected = false,
        label = "Humorous",
        tone = HUMOROUS
      )
    )

  val genreChips: ImmutableList<GenreChipUiState> =
    persistentListOf(
      GenreChipUiState(name = "Country"),
      GenreChipUiState(name = "Metal")
    )
}
