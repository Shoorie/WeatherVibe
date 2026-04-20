package com.weather.vibe.feature.profile.presentation

internal sealed interface ProfileAction {
  data object AboutClick : ProfileAction
  data object EditUsernameClick : ProfileAction
  data object EditUsernameDismiss : ProfileAction
  data object EditUsernameSubmit : ProfileAction
  data class UsernameChanged(val value: String) : ProfileAction
  data object NotificationsClick : ProfileAction
  data object PersonalizationClick : ProfileAction
  data object PrivacyClick : ProfileAction
}
