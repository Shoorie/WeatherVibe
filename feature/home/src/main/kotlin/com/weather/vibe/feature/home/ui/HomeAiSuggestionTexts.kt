package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

internal object HomeAiSuggestionTexts {

  @Composable
  fun aiBriefingLabel(): String =
    stringResource(R.string.ai_briefing_label)

  @Composable
  fun aiBriefingMusicHint(): String =
    stringResource(R.string.ai_briefing_music_hint)

  @Composable
  fun aiBriefingOutfitLabel(): String =
    stringResource(R.string.ai_briefing_outfit_label)

  @Composable
  fun aiBriefingRetryContentDescription(): String =
    stringResource(R.string.ai_briefing_retry_content_description)

  @Composable
  fun aiBriefingRetryLabel(): String =
    stringResource(R.string.ai_briefing_retry)

  @Composable
  fun aiBriefingUnavailable(): String =
    stringResource(R.string.ai_briefing_unavailable)

  @Composable
  fun aiBriefingPersonaReads(): String =
    stringResource(R.string.ai_briefing_persona_reads)

  @Composable
  fun aiBriefingOpenPersonalization(): String =
    stringResource(R.string.ai_briefing_open_personalization)

  @Composable
  fun aiBriefingPremiumComingSoon(): String =
    stringResource(R.string.ai_briefing_premium_coming_soon)

  @Composable
  fun aiBriefingLimitTitle(): String =
    stringResource(R.string.ai_briefing_limit_title)

  @Composable
  fun aiBriefingLimitPremium(): String =
    stringResource(R.string.ai_briefing_limit_premium)

  @Composable
  fun aiBriefingLimitWatch(): String =
    stringResource(R.string.ai_briefing_limit_watch)

  @Composable
  fun aiBriefingLimitReset(): String =
    stringResource(R.string.ai_briefing_limit_reset)

  @Composable
  fun dailyVibeSectionLabel(): String =
    stringResource(R.string.daily_vibe_section_label)

  @Composable
  fun findingBetterSuggestionsLabel(): String =
    stringResource(R.string.finding_better_suggestions)

  @Composable
  fun genreRemoveContentDescription(genre: String): String =
    stringResource(R.string.genre_remove_content_description, genre)

  @Composable
  fun moodPlaylistContentDescription(): String =
    stringResource(R.string.mood_playlist_content_description)

  @Composable
  fun moodPlaylistErrorTitle(): String =
    stringResource(R.string.mood_playlist_error_title)

  @Composable
  fun moodPlaylistLabel(): String =
    stringResource(R.string.mood_playlist_label)

  @Composable
  fun moodPlaylistSubtitle(): String =
    stringResource(R.string.mood_playlist_subtitle)

  @Composable
  fun moodPlaylistUnavailable(): String =
    stringResource(R.string.mood_playlist_unavailable)

  @Composable
  fun openInSpotify(): String =
    stringResource(R.string.open_in_spotify)

  @Composable
  fun openInYtMusic(): String =
    stringResource(R.string.open_in_yt_music)
}
