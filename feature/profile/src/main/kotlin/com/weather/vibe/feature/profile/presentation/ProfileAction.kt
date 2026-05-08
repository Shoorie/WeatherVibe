package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType

internal sealed interface ProfileAction {
  data object ContactClick : ProfileAction
  data object EditUsernameClick : ProfileAction
  data object EditUsernameDismiss : ProfileAction
  data object EditUsernameSubmit : ProfileAction
  data object LicensesClick : ProfileAction
  data object NotificationsClick : ProfileAction
  data object PersonalizationClick : ProfileAction
  data object PrivacyClick : ProfileAction
  data class StatClick(val type: ProfileStatType) : ProfileAction
  data class ThemeSelect(val mode: ThemeMode) : ProfileAction
  data class UsernameChanged(val value: String) : ProfileAction
  data object VibeRowClick : ProfileAction
}
