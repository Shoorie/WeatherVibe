package com.weather.vibe.feature.onboarding.presentation.welcome

internal sealed interface WelcomeAction {
  data object NextClick : WelcomeAction
  data class NotificationsPermissionResult(val granted: Boolean) : WelcomeAction
  data object SkipClick : WelcomeAction
  data object SkipNotificationsClick : WelcomeAction
  data class SlideChange(val slideIndex: Int) : WelcomeAction
}
