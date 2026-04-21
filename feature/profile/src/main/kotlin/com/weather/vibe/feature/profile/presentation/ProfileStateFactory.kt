package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.profile.model.ProfileSummary
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import com.weather.vibe.feature.profile.ui.ProfileResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.core.annotation.Factory

@Factory
internal class ProfileStateFactory(private val resources: ProfileResources) {

  fun initial(): ProfileUiState =
    ProfileUiState(
      header = createHeader(
        username = EMPTY_USERNAME,
        briefToneLabel = EMPTY_TONE_LABEL,
        quote = EMPTY_QUOTE,
        usageDays = INITIAL_USAGE_DAYS
      ),
      quickStats = createStats(
        usageDays = INITIAL_USAGE_DAYS,
        locationsCount = INITIAL_LOCATIONS_COUNT
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = EMPTY_USERNAME,
        canSave = false
      ),
      usageDays = INITIAL_USAGE_DAYS,
      locationsCount = INITIAL_LOCATIONS_COUNT
    )

  fun withProfile(state: ProfileUiState, profile: ProfileSummary): ProfileUiState =
    state.copy(
      header = createHeader(
        username = profile.username,
        briefToneLabel = state.header.briefToneLabel,
        quote = state.header.quote,
        usageDays = profile.usageDays
      ),
      quickStats = createStats(
        usageDays = profile.usageDays,
        locationsCount = state.locationsCount
      ),
      usageDays = profile.usageDays
    )

  fun withBriefTone(state: ProfileUiState, tone: BriefTone): ProfileUiState =
    state.copy(
      header = state.header.copy(
        briefToneLabel = resources.briefToneLabel(tone = tone),
        quote = resources.heroQuote(tone = tone)
      )
    )

  fun triggerEditSheet(state: ProfileUiState): ProfileUiState =
    state.copy(
      editSheet = ProfileEditSheetUiState(
        isVisible = true,
        username = state.header.username,
        canSave = state.header.username.trim().isNotEmpty()
      )
    )

  fun dismissEditSheet(state: ProfileUiState): ProfileUiState =
    state.copy(editSheet = state.editSheet.copy(isVisible = false))

  fun editUsername(state: ProfileUiState, value: String): ProfileUiState =
    state.copy(
      editSheet = state.editSheet.copy(
        username = value,
        canSave = value.trim().isNotEmpty()
      )
    )

  private fun createHeader(
    username: String,
    briefToneLabel: String,
    quote: String,
    usageDays: Int
  ): ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = username,
      greeting = createGreeting(username = username),
      subtitle = createSubtitle(username = username, usageDays = usageDays),
      briefToneLabel = briefToneLabel,
      quote = quote
    )

  private fun createGreeting(username: String): String =
    when {
      username.isBlank() -> resources.unnamedGreeting()
      else -> resources.greeting(username = username)
    }

  private fun createSubtitle(username: String, usageDays: Int): String =
    when {
      username.isBlank() -> resources.unnamedSubtitle()
      usageDays <= NO_USAGE_DAYS -> resources.unnamedSubtitle()
      else -> resources.daysWithAppSubtitle(days = usageDays)
    }

  private fun createStats(
    usageDays: Int,
    locationsCount: Int
  ): ImmutableList<ProfileStatUiState> =
    persistentListOf(
      ProfileStatUiState(
        id = STAT_LOCATIONS,
        label = resources.locationsStatLabel(),
        value = locationsCount.toString()
      ),
      ProfileStatUiState(
        id = STAT_STREAK,
        label = resources.streakStatLabel(),
        value = usageDays.toString()
      )
    )

  private companion object {
    const val EMPTY_USERNAME = ""
    const val EMPTY_TONE_LABEL = ""
    const val EMPTY_QUOTE = ""
    const val INITIAL_USAGE_DAYS = 0
    const val INITIAL_LOCATIONS_COUNT = 1
    const val NO_USAGE_DAYS = 0
    const val STAT_LOCATIONS = "locations"
    const val STAT_STREAK = "streak"
  }
}
