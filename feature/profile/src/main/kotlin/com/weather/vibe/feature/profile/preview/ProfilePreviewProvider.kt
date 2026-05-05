package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceOptionUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceRowUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded
import kotlinx.collections.immutable.persistentListOf

internal class ProfilePreviewProvider :
  PreviewParameterProvider<ProfileUiState> {

  private val namedHeader =
    ProfileHeaderPreviewProvider().named

  private val unnamedHeader =
    ProfileHeaderPreviewProvider().unnamed

  private val statsLoaded = persistentListOf(
    ProfileStatUiState(
      emoji = "📍",
      label = "Locations",
      onClickLabel = "Open locations",
      type = LOCATIONS,
      value = "3"
    ),
    ProfileStatUiState(
      emoji = "🌅",
      label = "Morning brief",
      onClickLabel = "Open notifications",
      type = MORNING_BRIEF,
      value = "On"
    ),
    ProfileStatUiState(
      emoji = "🔔",
      label = "Alerts",
      onClickLabel = "Open notifications",
      type = ALERTS,
      value = "On"
    )
  )

  private val statsUnnamed = persistentListOf(
    ProfileStatUiState(
      emoji = "📍",
      label = "Locations",
      onClickLabel = "Open locations",
      type = LOCATIONS,
      value = "1"
    ),
    ProfileStatUiState(
      emoji = "🌅",
      label = "Morning brief",
      onClickLabel = "Open notifications",
      type = MORNING_BRIEF,
      value = "Off"
    ),
    ProfileStatUiState(
      emoji = "🔔",
      label = "Alerts",
      onClickLabel = "Open notifications",
      type = ALERTS,
      value = "Off"
    )
  )

  private val vibeLoaded: Loaded =
    Loaded(
      averageLabel = "4.5/5",
      onClickLabel = "Open vibe history",
      streakLabel = "2 days in a row 🔥",
      title = "Your vibe"
    )

  private val vibeEmpty: Empty =
    Empty(
      ctaLabel = "Rate your first day",
      onClickLabel = "Rate your first day",
      title = "Your vibe"
    )

  private val appearanceAuto: ProfileAppearanceRowUiState =
    appearanceRow(current = AUTO)

  private val appearanceDark: ProfileAppearanceRowUiState =
    appearanceRow(current = DARK)

  private val loaded: ProfileUiState =
    ProfileUiState(
      appearanceRow = appearanceAuto,
      editSheet = sheet(),
      header = namedHeader,
      locationsCount = 3,
      morningBriefEnabled = true,
      quickStats = statsLoaded,
      vibeRow = vibeLoaded,
      weatherAlertsEnabled = true
    )

  private val unnamed: ProfileUiState =
    ProfileUiState(
      appearanceRow = appearanceDark,
      editSheet = sheet(),
      header = unnamedHeader,
      locationsCount = 1,
      morningBriefEnabled = false,
      quickStats = statsUnnamed,
      vibeRow = vibeEmpty,
      weatherAlertsEnabled = false
    )

  override val values: Sequence<ProfileUiState> =
    sequenceOf(loaded, unnamed)

  private fun sheet(): ProfileEditSheetUiState =
    ProfileEditSheetUiState(
      isVisible = false,
      username = "",
      canSave = false
    )

  private fun appearanceRow(current: ThemeMode): ProfileAppearanceRowUiState =
    ProfileAppearanceRowUiState(
      body = "Light, dark or follow system",
      current = current,
      options = persistentListOf(
        appearanceOption(current = current, mode = LIGHT, label = "Light"),
        appearanceOption(current = current, mode = AUTO, label = "Auto"),
        appearanceOption(current = current, mode = DARK, label = "Dark")
      ),
      title = "Appearance"
    )

  private fun appearanceOption(
    current: ThemeMode,
    mode: ThemeMode,
    label: String
  ): ProfileAppearanceOptionUiState =
    ProfileAppearanceOptionUiState(
      isSelected = current == mode,
      label = label,
      mode = mode
    )
}
