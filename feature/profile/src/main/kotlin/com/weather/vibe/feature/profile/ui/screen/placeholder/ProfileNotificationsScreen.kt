package com.weather.vibe.feature.profile.ui.screen.placeholder

import androidx.compose.runtime.Composable
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderNotificationsBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderNotificationsTitle

@Composable
fun ProfileNotificationsScreen(onNavigateBack: () -> Unit) {
  val title = placeholderNotificationsTitle()
  ProfilePlaceholderContent(
    topBarTitle = title,
    title = title,
    body = placeholderNotificationsBody(),
    onNavigateBack = onNavigateBack
  )
}
