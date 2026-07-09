package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.feature.home.presentation.state.BriefingPersonaUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory

@Factory
internal class AiSuggestionSectionFactory(
  private val playlistFactory: PlaylistStateFactory,
  private val resources: HomeResources
) {

  fun buildBriefing(suggestion: WeatherSuggestion, tone: BriefTone): BriefingUiState.Loaded =
    BriefingUiState.Loaded(
      persona = personaOf(tone),
      text = suggestion.briefText,
      outfit = suggestion.outfitSuggestion
    )

  fun buildLimit(previous: WeatherSuggestion?, tone: BriefTone): BriefingUiState.Limit =
    BriefingUiState.Limit(
      persona = personaOf(tone),
      teaser = previous?.briefText.orEmpty(),
      outfit = previous?.outfitSuggestion
    )

  fun buildPlaylist(suggestion: WeatherSuggestion): PlaylistUiState.Loaded =
    playlistFactory.create(suggestion)

  fun buildDailyVibe(vibe: DailyVibe): DailyVibeUiState =
    DailyVibeUiState(
      contentDescription = resources.dailyVibeContentDescription(vibe.mood, vibe.score),
      emoji = resources.dailyVibeEmoji(vibe.mood),
      oneLiner = resources.dailyVibeOneLiner(vibe.mood),
      summary = resources.dailyVibeSummary(vibe.score, vibe.mood)
    )

  private fun personaOf(tone: BriefTone): BriefingPersonaUiState =
    BriefingPersonaUiState(colorKey = tone.toColorKey())

  private fun BriefTone.toColorKey(): PersonaColorKey = when (this) {
    BriefTone.WITTY_AND_FRIENDLY -> PersonaColorKey.WITTY_AND_FRIENDLY
    BriefTone.FORMAL -> PersonaColorKey.FORMAL
    BriefTone.HUMOROUS -> PersonaColorKey.HUMOROUS
    BriefTone.COACH -> PersonaColorKey.COACH
    BriefTone.SCI_FI -> PersonaColorKey.SCI_FI
    BriefTone.RPG -> PersonaColorKey.RPG
    BriefTone.CINEMATIC -> PersonaColorKey.CINEMATIC
    BriefTone.CYNIC -> PersonaColorKey.CYNIC
  }
}
