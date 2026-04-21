package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.profile.model.ProfileSummary
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import com.weather.vibe.feature.profile.ui.ProfileResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.core.annotation.Factory

@Factory
internal class ProfileStateFactory(private val resources: ProfileResources) {

  fun initial(): ProfileUiState {
    val initialState = ProfileUiState(
      header = createHeader(
        username = EMPTY_USERNAME,
        briefToneLabel = EMPTY_TONE_LABEL,
        quote = EMPTY_QUOTE,
        usageDays = INITIAL_USAGE_DAYS
      ),
      quickStats = persistentListOf(),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = EMPTY_USERNAME,
        canSave = false
      ),
      locationsCount = INITIAL_LOCATIONS_COUNT,
      morningBriefEnabled = false,
      alertsEnabled = false
    )
    return initialState.rebuildStats()
  }

  fun withProfile(state: ProfileUiState, profile: ProfileSummary): ProfileUiState =
    state.copy(
      header = createHeader(
        username = profile.username,
        briefToneLabel = state.header.briefToneLabel,
        quote = state.header.quote,
        usageDays = profile.usageDays
      )
    ).rebuildStats()

  fun withSettings(state: ProfileUiState, settings: UserSettings): ProfileUiState =
    state.copy(
      header = state.header.copy(
        briefToneLabel = resources.briefToneLabel(tone = settings.briefTone),
        quote = resources.heroQuote(tone = settings.briefTone)
      ),
      morningBriefEnabled = settings.morningBriefEnabled,
      alertsEnabled = settings.alertsEnabled
    ).rebuildStats()

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

  private fun ProfileUiState.rebuildStats(): ProfileUiState =
    copy(
      quickStats = createStats(
        locationsCount = locationsCount,
        morningBriefEnabled = morningBriefEnabled,
        alertsEnabled = alertsEnabled
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
      avatarInitial = createAvatarInitial(username = username),
      greeting = createGreeting(username = username),
      subtitle = createSubtitle(username = username, usageDays = usageDays),
      briefToneLabel = briefToneLabel,
      quote = quote
    )

  private fun createAvatarInitial(username: String): String {
    val trimmed = username.trim()
    return when {
      trimmed.isEmpty() -> resources.unnamedAvatar()
      else -> trimmed.first().uppercaseChar().toString()
    }
  }

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
    locationsCount: Int,
    morningBriefEnabled: Boolean,
    alertsEnabled: Boolean
  ): ImmutableList<ProfileStatUiState> =
    persistentListOf(
      ProfileStatUiState(
        type = LOCATIONS,
        label = resources.locationsStatLabel(),
        value = locationsCount.toString(),
        onClickLabel = resources.locationsStatClickLabel()
      ),
      ProfileStatUiState(
        type = MORNING_BRIEF,
        label = resources.morningBriefStatLabel(),
        value = resources.statStatus(enabled = morningBriefEnabled),
        onClickLabel = resources.morningBriefStatClickLabel()
      ),
      ProfileStatUiState(
        type = ALERTS,
        label = resources.alertsStatLabel(),
        value = resources.statStatus(enabled = alertsEnabled),
        onClickLabel = resources.alertsStatClickLabel()
      )
    )

  private companion object {
    const val EMPTY_USERNAME = ""
    const val EMPTY_TONE_LABEL = ""
    const val EMPTY_QUOTE = ""
    const val INITIAL_USAGE_DAYS = 0
    const val INITIAL_LOCATIONS_COUNT = 1
    const val NO_USAGE_DAYS = 0
  }
}
