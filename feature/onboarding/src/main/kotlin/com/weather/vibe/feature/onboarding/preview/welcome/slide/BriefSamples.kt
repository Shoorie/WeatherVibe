package com.weather.vibe.feature.onboarding.preview.welcome.slide

import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefToneUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object BriefSamples {

  fun tones(): ImmutableList<BriefToneUiState> = persistentListOf(
    BriefToneUiState(
      label = "Witty",
      quote = "24° and sunny. A perfect day to leave the jacket at home."
    ),
    BriefToneUiState(
      label = "Formal",
      quote = "Temperature 24°C, sunny, west wind 6 km/h."
    ),
    BriefToneUiState(
      label = "Humorous",
      quote = "The sky is feeling generous today — 24°, not a cloud in sight."
    )
  )
}
