package com.weather.vibe.feature.profile.ui.screen.placeholder

import androidx.compose.runtime.Composable
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderAboutBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderAboutTitle

@Composable
fun ProfileAboutScreen(onNavigateBack: () -> Unit) {
  val title = placeholderAboutTitle()
  ProfilePlaceholderContent(
    topBarTitle = title,
    title = title,
    body = placeholderAboutBody(),
    onNavigateBack = onNavigateBack
  )
}
