package com.weather.vibe.feature.profile.presentation

internal sealed interface ProfileEvent {
  data object OpenAbout : ProfileEvent
  data object OpenLocations : ProfileEvent
  data object OpenNotifications : ProfileEvent
  data object OpenPersonalization : ProfileEvent
  data object OpenPrivacy : ProfileEvent
}
