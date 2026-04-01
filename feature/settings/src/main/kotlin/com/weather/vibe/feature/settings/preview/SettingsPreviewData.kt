package com.weather.vibe.feature.settings.preview

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.settings.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.presentation.state.GenreChipSettingsUiState

internal object SettingsPreviewData {

  val briefToneOptions: List<BriefToneOptionUiState> = listOf(
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
      description = "Fun, playful, and punny",
      isSelected = false,
      label = "Humorous",
      tone = HUMOROUS
    )
  )

  val genreChips: List<GenreChipSettingsUiState> = listOf(
    GenreChipSettingsUiState(name = "classical"),
    GenreChipSettingsUiState(name = "jazz")
  )
}
