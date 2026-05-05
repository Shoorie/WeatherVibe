package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.domain.viberating.model.VibeOverview
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceOptionUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceRowUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded
import com.weather.vibe.feature.profile.ui.ProfileResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.core.annotation.Factory

@Factory
internal class ProfileStateFactory(private val resources: ProfileResources) {

  fun initial(): ProfileUiState =
    ProfileUiState(
      appearanceRow = null,
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = EMPTY_USERNAME,
        canSave = false
      ),
      header = createHeader(username = EMPTY_USERNAME, briefToneLabel = EMPTY_TONE_LABEL),
      locationsCount = INITIAL_LOCATIONS_COUNT,
      morningBriefEnabled = false,
      quickStats = createStats(
        locationsCount = INITIAL_LOCATIONS_COUNT,
        morningBriefEnabled = false,
        weatherAlertsEnabled = false
      ),
      vibeRow = createEmptyVibeRow(),
      weatherAlertsEnabled = false
    )

  fun create(
    state: ProfileUiState,
    snapshot: ProfileSnapshot
  ): ProfileUiState {

    val settings = snapshot.settingsResult.getOrNull()
    val locationsCount = snapshot.favoritesCountResult.getOrDefault(state.locationsCount)
    val morningBriefEnabled = settings?.morningBriefEnabled ?: state.morningBriefEnabled
    val weatherAlertsEnabled = settings?.weatherAlertsEnabled ?: state.weatherAlertsEnabled
    val briefToneLabel = settings
      ?.let { resources.briefToneLabel(it.briefTone) }
      ?: state.header.briefToneLabel

    return state.copy(
      appearanceRow = createAppearanceRow(snapshot.themeMode),
      header = createHeader(
        username = snapshot.profile.username,
        briefToneLabel = briefToneLabel
      ),
      locationsCount = locationsCount,
      morningBriefEnabled = morningBriefEnabled,
      quickStats = createStats(
        locationsCount = locationsCount,
        morningBriefEnabled = morningBriefEnabled,
        weatherAlertsEnabled = weatherAlertsEnabled
      ),
      vibeRow = createVibeRow(snapshot.vibeOverview),
      weatherAlertsEnabled = weatherAlertsEnabled
    )
  }

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

  private fun createHeader(username: String, briefToneLabel: String): ProfileHeaderUiState =
    ProfileHeaderUiState(
      avatarInitial = createAvatarInitial(username = username),
      briefToneLabel = briefToneLabel,
      greeting = createGreeting(username = username),
      showWavingHand = username.isNotBlank(),
      subtitle = createSubtitle(username = username),
      username = username
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

  private fun createSubtitle(username: String): String =
    when {
      username.isBlank() -> resources.unnamedSubtitle()
      else -> resources.returningSubtitle()
    }

  private fun createStats(
    locationsCount: Int,
    morningBriefEnabled: Boolean,
    weatherAlertsEnabled: Boolean
  ): ImmutableList<ProfileStatUiState> =
    persistentListOf(
      ProfileStatUiState(
        emoji = resources.locationsStatEmoji(),
        label = resources.locationsStatLabel(),
        onClickLabel = resources.locationsStatClickLabel(),
        type = LOCATIONS,
        value = locationsCount.toString()
      ),
      ProfileStatUiState(
        emoji = resources.morningBriefStatEmoji(),
        label = resources.morningBriefStatLabel(),
        onClickLabel = resources.morningBriefStatClickLabel(),
        type = MORNING_BRIEF,
        value = resources.statStatus(enabled = morningBriefEnabled)
      ),
      ProfileStatUiState(
        emoji = resources.alertsStatEmoji(),
        label = resources.alertsStatLabel(),
        onClickLabel = resources.alertsStatClickLabel(),
        type = ALERTS,
        value = resources.statStatus(enabled = weatherAlertsEnabled)
      )
    )

  private fun createVibeRow(overview: VibeOverview): ProfileVibeRowUiState =
    when {
      overview.hasEntries -> Loaded(
        averageLabel = resources.vibeAverageLabel(value = overview.averageRating),
        onClickLabel = resources.vibeLoadedClickLabel(),
        streakLabel = streakLabelOrNull(overview.streakDays),
        title = resources.vibeTitle()
      )

      else -> createEmptyVibeRow()
    }

  private fun createEmptyVibeRow(): Empty =
    Empty(
      ctaLabel = resources.vibeEmptyCta(),
      onClickLabel = resources.vibeEmptyClickLabel(),
      title = resources.vibeTitle()
    )

  private fun streakLabelOrNull(streakDays: Int): String? =
    when {
      streakDays >= MIN_STREAK_DAYS -> resources.vibeStreakLabel(streakDays)
      else -> null
    }

  private fun createAppearanceRow(current: ThemeMode): ProfileAppearanceRowUiState =
    ProfileAppearanceRowUiState(
      body = resources.appearanceBody(),
      current = current,
      options = persistentListOf(
        appearanceOption(current = current, mode = LIGHT),
        appearanceOption(current = current, mode = AUTO),
        appearanceOption(current = current, mode = DARK)
      ),
      title = resources.appearanceTitle()
    )

  private fun appearanceOption(
    current: ThemeMode,
    mode: ThemeMode
  ): ProfileAppearanceOptionUiState =
    ProfileAppearanceOptionUiState(
      isSelected = current == mode,
      label = resources.appearanceOptionLabel(mode = mode),
      mode = mode
    )

  private companion object {
    const val EMPTY_USERNAME = ""
    const val EMPTY_TONE_LABEL = ""
    const val INITIAL_LOCATIONS_COUNT = 0
    const val MIN_STREAK_DAYS = 2
  }
}
