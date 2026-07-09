package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.CINEMATIC
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.COACH
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.CYNIC
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.FORMAL
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.HUMOROUS
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.RPG
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.SCI_FI
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.personalization.presentation.state.GenreChipUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.NarratorUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PaywallUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonaUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory

@Factory
internal class PersonalizationStateFactory(
  private val resources: PersonalizationResources
) {

  fun create(
    availableTones: List<BriefTone>,
    isPremium: Boolean,
    lockedTones: Set<BriefTone>,
    paywallTone: BriefTone?,
    settings: UserSettings
  ): Loaded {
    val selected = settings.briefTone
    return Loaded(
      genreChips = createExcludedGenreChips(excluded = settings.excludedGenres),
      hasExcludedGenres = settings.excludedGenres.isNotEmpty(),
      isCelsius = settings.temperatureUnit == CELSIUS,
      isPremium = isPremium,
      narrator = narratorOf(selected),
      paywall = paywallTone?.let(::paywallOf),
      personas = availableTones.map { tone ->
        personaOf(tone = tone, selected = selected, locked = tone in lockedTones)
      }.toImmutableList(),
      premiumToneCount = availableTones.count { it.isPremium }
    )
  }

  fun createError(): Error =
    Error(message = resources.defaultError())

  private fun narratorOf(tone: BriefTone): NarratorUiState =
    NarratorUiState(
      colorKey = tone.toColorKey(),
      isPremium = tone.isPremium,
      name = resources.briefToneLabel(tone),
      sample = resources.briefToneSample(tone),
      subtitle = resources.briefToneDescription(tone)
    )

  private fun personaOf(
    tone: BriefTone,
    selected: BriefTone,
    locked: Boolean
  ): PersonaUiState =
    PersonaUiState(
      colorKey = tone.toColorKey(),
      isLocked = locked,
      isPremium = tone.isPremium,
      isSelected = tone == selected,
      label = resources.briefToneShortLabel(tone),
      tone = tone
    )

  private fun paywallOf(tone: BriefTone): PaywallUiState =
    PaywallUiState(
      colorKey = tone.toColorKey(),
      name = resources.briefToneLabel(tone),
      sample = resources.briefToneSample(tone),
      tone = tone
    )

  private fun createExcludedGenreChips(
    excluded: Set<String>
  ): ImmutableList<GenreChipUiState> =
    excluded.sorted().map { genre ->
      GenreChipUiState(name = genre)
    }.toImmutableList()

  private fun BriefTone.toColorKey(): PersonaColorKey = when (this) {
    BriefTone.WITTY_AND_FRIENDLY -> WITTY_AND_FRIENDLY
    BriefTone.FORMAL -> FORMAL
    BriefTone.HUMOROUS -> HUMOROUS
    BriefTone.COACH -> COACH
    BriefTone.SCI_FI -> SCI_FI
    BriefTone.RPG -> RPG
    BriefTone.CINEMATIC -> CINEMATIC
    BriefTone.CYNIC -> CYNIC
  }
}
