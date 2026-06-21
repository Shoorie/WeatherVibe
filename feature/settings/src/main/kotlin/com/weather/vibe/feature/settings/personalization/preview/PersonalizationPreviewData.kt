package com.weather.vibe.feature.settings.personalization.preview

import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.personalization.presentation.state.GenreChipUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.NarratorUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonaUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object PersonalizationPreviewData {

  val narrator: NarratorUiState =
    NarratorUiState(
      colorKey = PersonaColorKey.WITTY_AND_FRIENDLY,
      emoji = "😊",
      isPremium = false,
      name = "Witty & friendly",
      sample = "Hey! A bit cloudy today, around 10°C, but you won't catch any rain.",
      subtitle = "Light, warm and conversational"
    )

  val personas: ImmutableList<PersonaUiState> =
    persistentListOf(
      persona(
        colorKey = PersonaColorKey.WITTY_AND_FRIENDLY,
        emoji = "😊",
        isSelected = true,
        label = "Witty",
        tone = BriefTone.WITTY_AND_FRIENDLY
      ),
      persona(
        colorKey = PersonaColorKey.FORMAL,
        emoji = "📋",
        label = "Formal",
        tone = BriefTone.FORMAL
      ),
      persona(
        colorKey = PersonaColorKey.COACH,
        emoji = "🏋️",
        isLocked = true,
        isPremium = true,
        label = "Coach",
        tone = BriefTone.COACH
      ),
      persona(
        colorKey = PersonaColorKey.CINEMATIC,
        emoji = "🎬",
        isLocked = true,
        isPremium = true,
        label = "Cinematic",
        tone = BriefTone.CINEMATIC
      )
    )

  val genreChips: ImmutableList<GenreChipUiState> =
    persistentListOf(
      GenreChipUiState(name = "Country"),
      GenreChipUiState(name = "Metal")
    )

  val loaded: Loaded =
    Loaded(
      genreChips = genreChips,
      hasExcludedGenres = true,
      isCelsius = true,
      isPremium = false,
      narrator = narrator,
      paywall = null,
      personas = personas,
      premiumToneCount = 5
    )

  private fun persona(
    colorKey: PersonaColorKey,
    emoji: String,
    isLocked: Boolean = false,
    isPremium: Boolean = false,
    isSelected: Boolean = false,
    label: String,
    tone: BriefTone
  ): PersonaUiState =
    PersonaUiState(
      colorKey = colorKey,
      emoji = emoji,
      isLocked = isLocked,
      isPremium = isPremium,
      isSelected = isSelected,
      label = label,
      tone = tone
    )
}
