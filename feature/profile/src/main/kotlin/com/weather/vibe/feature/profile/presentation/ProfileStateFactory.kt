package com.weather.vibe.feature.profile.presentation

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
    create(username = EMPTY_USERNAME)

  fun withUsername(username: String): ProfileUiState =
    create(username = username)

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

  private fun create(username: String): ProfileUiState =
    ProfileUiState(
      header = createHeader(username = username),
      quickStats = createStats(),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = username,
        canSave = username.trim().isNotEmpty()
      )
    )

  private fun createHeader(username: String): ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = username,
      greeting = createGreeting(username = username),
      subtitle = createSubtitle(username = username),
      briefToneLabel = BRIEF_TONE_PLACEHOLDER
    )

  private fun createGreeting(username: String): String =
    when (username.isBlank()) {
      true -> resources.ctaGreeting()
      false -> resources.greeting(username = username)
    }

  private fun createSubtitle(username: String): String =
    when (username.isBlank()) {
      true -> resources.ctaSubtitle()
      false -> resources.daysWithAppSubtitle(days = USAGE_DAYS)
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
    const val BRIEF_TONE_PLACEHOLDER = "Chill"
    const val USAGE_DAYS = 1
    const val LOCATIONS_COUNT = 1
    const val STAT_LOCATIONS = "locations"
    const val STAT_STREAK = "streak"
  }
}
