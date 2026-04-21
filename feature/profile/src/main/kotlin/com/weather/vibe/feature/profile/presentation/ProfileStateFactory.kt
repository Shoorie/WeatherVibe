package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import com.weather.vibe.feature.profile.ui.ProfileDefaults.MaxUsernameLength
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
        quote = EMPTY_QUOTE
      ),
      quickStats = createStats(),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = EMPTY_USERNAME,
        canSave = false
      )
    )

  fun withUsername(state: ProfileUiState, username: String): ProfileUiState =
    state.copy(
      header = createHeader(
        username = username,
        briefToneLabel = state.header.briefToneLabel,
        quote = state.header.quote
      )
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

  fun editUsername(state: ProfileUiState, value: String): ProfileUiState {
    val trimmed = value.take(MaxUsernameLength)
    return state.copy(
      editSheet = state.editSheet.copy(
        username = trimmed,
        canSave = trimmed.trim().isNotEmpty()
      )
    )
  }

  private fun createHeader(
    username: String,
    briefToneLabel: String,
    quote: String
  ): ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = username,
      greeting = createGreeting(username = username),
      subtitle = createSubtitle(username = username),
      briefToneLabel = briefToneLabel,
      quote = quote
    )

  private fun createGreeting(username: String): String =
    when {
      username.isBlank() -> resources.ctaGreeting()
      else -> resources.greeting(username = username)
    }

  private fun createSubtitle(username: String): String =
    when {
      username.isBlank() -> resources.ctaSubtitle()
      else -> resources.daysWithAppSubtitle(days = USAGE_DAYS)
    }

  private fun createStats(): ImmutableList<ProfileStatUiState> =
    persistentListOf(
      ProfileStatUiState(
        id = STAT_LOCATIONS,
        label = resources.locationsStatLabel(),
        value = LOCATIONS_COUNT.toString()
      ),
      ProfileStatUiState(
        id = STAT_STREAK,
        label = resources.streakStatLabel(),
        value = USAGE_DAYS.toString()
      )
    )

  private companion object {
    const val EMPTY_USERNAME = ""
    const val EMPTY_TONE_LABEL = ""
    const val EMPTY_QUOTE = ""
    const val USAGE_DAYS = 1
    const val LOCATIONS_COUNT = 1
    const val STAT_LOCATIONS = "locations"
    const val STAT_STREAK = "streak"
  }
}
