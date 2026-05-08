package com.weather.vibe.feature.profile.presentation

internal sealed interface ProfileEvent {
  data object OpenContact : ProfileEvent
  data object OpenLicenses : ProfileEvent
  data object OpenLocations : ProfileEvent
  data object OpenNotifications : ProfileEvent
  data object OpenPersonalization : ProfileEvent
  data object OpenPrivacy : ProfileEvent
  data object OpenVibeHistory : ProfileEvent
}
