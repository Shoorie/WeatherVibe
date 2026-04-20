package com.weather.vibe.feature.profile.ui.screen.placeholder

import androidx.compose.runtime.Composable
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderPrivacyBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderPrivacyTitle

@Composable
fun ProfilePrivacyScreen(onNavigateBack: () -> Unit) {
  val title = placeholderPrivacyTitle()
  ProfilePlaceholderContent(
    topBarTitle = title,
    title = title,
    body = placeholderPrivacyBody(),
    onNavigateBack = onNavigateBack
  )
}
