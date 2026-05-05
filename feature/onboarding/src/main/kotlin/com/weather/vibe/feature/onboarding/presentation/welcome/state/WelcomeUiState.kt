package com.weather.vibe.feature.onboarding.presentation.welcome.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefToneUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlaceCardUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyNotificationCardUiState
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class WelcomeUiState(
  val briefTones: ImmutableList<BriefToneUiState>,
  val canRequestNotificationsPermission: Boolean,
  val greetings: ImmutableList<String>,
  val isFinalSlide: Boolean,
  val notificationCards: ImmutableList<ReadyNotificationCardUiState>,
  val places: ImmutableList<PlaceCardUiState>,
  val primaryActionLabel: String,
  val skipNotificationsLabel: String?,
  val skipVisible: Boolean,
  val slide: WelcomeSlide,
  val slideIndex: Int,
  val totalSlides: Int
)
