package com.weather.vibe.feature.profile.ui.screen.placeholder

import androidx.compose.runtime.Composable
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderPersonalizationBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.placeholderPersonalizationTitle

@Composable
fun ProfilePersonalizationScreen(onNavigateBack: () -> Unit) {
  val title = placeholderPersonalizationTitle()
  ProfilePlaceholderContent(
    topBarTitle = title,
    title = title,
    body = placeholderPersonalizationBody(),
    onNavigateBack = onNavigateBack
  )
}
