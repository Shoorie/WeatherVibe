package com.weather.vibe.feature.home.presentation.state

import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import kotlinx.collections.immutable.toImmutableList

internal fun HomeUiState.withBriefing(briefing: BriefingUiState): HomeUiState =
  asLoaded { loaded ->
    loaded.copy(aiSuggestion = loaded.aiSuggestion.copy(briefing = briefing))
  }

internal fun HomeUiState.withPlaylist(playlist: PlaylistUiState): HomeUiState =
  asLoaded { loaded ->
    loaded.copy(aiSuggestion = loaded.aiSuggestion.copy(playlist = playlist))
  }

internal fun HomeUiState.withSuggestion(
  briefing: BriefingUiState,
  playlist: PlaylistUiState
): HomeUiState =
  asLoaded { loaded ->
    loaded.copy(
      aiSuggestion = loaded.aiSuggestion.copy(briefing = briefing, playlist = playlist)
    )
  }

internal fun HomeUiState.withDailyVibe(card: DailyVibeCardUiState): HomeUiState =
  asLoaded { loaded -> loaded.copy(dailyVibe = card) }

internal fun HomeUiState.withAlert(alert: HomeAlertUiState?): HomeUiState =
  asLoaded { loaded -> loaded.copy(alert = alert) }

internal fun HomeUiState.rejectGenre(genre: String): HomeUiState =
  asLoaded { loaded ->
    val playlist = loaded.aiSuggestion.playlist as? PlaylistUiState.Loaded
      ?: return@asLoaded loaded
    loaded.copy(
      aiSuggestion = loaded.aiSuggestion.copy(
        playlist = playlist.copy(genres = playlist.genres.markRejected(genre))
      )
    )
  }

private inline fun HomeUiState.asLoaded(block: (Loaded) -> HomeUiState): HomeUiState =
  if (this is Loaded) block(this) else this

private fun List<GenreChipUiState>.markRejected(genre: String) =
  map { if (it.name == genre) it.copy(isRejecting = true) else it }.toImmutableList()
