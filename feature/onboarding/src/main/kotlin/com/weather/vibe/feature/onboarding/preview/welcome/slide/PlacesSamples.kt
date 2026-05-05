package com.weather.vibe.feature.onboarding.preview.welcome.slide

import com.weather.vibe.core.designsystem.theme.category.CategoryTagPalette
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlaceCardUiState
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeEmojis
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object PlacesSamples {

  fun places(): ImmutableList<PlaceCardUiState> = persistentListOf(
    PlaceCardUiState(
      city = "London",
      emoji = WelcomeEmojis.rain(),
      region = "United Kingdom",
      tagBackground = CategoryTagPalette.Sky,
      tagLabel = "Home",
      temperature = "12°"
    ),
    PlaceCardUiState(
      city = "Berlin",
      emoji = WelcomeEmojis.partlyCloudy(),
      region = "Germany",
      tagBackground = CategoryTagPalette.Pink,
      tagLabel = "Work",
      temperature = "11°"
    ),
    PlaceCardUiState(
      city = "Lisbon",
      emoji = WelcomeEmojis.sunny(),
      region = "Portugal",
      tagBackground = CategoryTagPalette.Green,
      tagLabel = "Holiday",
      temperature = "22°"
    ),
    PlaceCardUiState(
      city = "Paris",
      emoji = WelcomeEmojis.partlyCloudy(),
      region = "France",
      tagBackground = CategoryTagPalette.Orange,
      tagLabel = "Trip",
      temperature = "16°"
    ),
    PlaceCardUiState(
      city = "Tokyo",
      emoji = WelcomeEmojis.mostlySunny(),
      region = "Japan",
      tagBackground = CategoryTagPalette.Violet,
      tagLabel = "Family",
      temperature = "18°"
    )
  )
}
