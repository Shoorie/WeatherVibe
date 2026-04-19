package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.weather.model.WeatherSuggestion
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

  fun buildBriefing(suggestion: WeatherSuggestion): BriefingUiState.Loaded =
    BriefingUiState.Loaded(
      text = suggestion.briefText,
      outfit = suggestion.outfitSuggestion
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
}
