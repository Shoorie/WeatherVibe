package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonaUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreviewData
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_CAROUSEL
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_GENRES
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_NARRATOR
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_TEMPERATURE
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_UPSELL
import com.weather.vibe.feature.settings.personalization.ui.component.genres.ExcludedGenresSection
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorCarousel
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorHero
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorUpsell
import com.weather.vibe.feature.settings.personalization.ui.component.temperature.TemperatureSection

@Composable
internal fun PersonalizationLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  callbacks: PersonalizationCallbacks
) {

  val contentPadding = remember {
    PaddingValues(top = Medium, bottom = ExtraLarge)
  }
  val horizontalInset = remember { Modifier.padding(horizontal = Medium) }

  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    item(key = KEY_NARRATOR) {
      NarratorHero(modifier = horizontalInset, narrator = state.narrator)
    }
    item(key = KEY_CAROUSEL) {
      NarratorCarousel(
        onPersonaClick = { persona -> callbacks.onPersonaTap(persona) },
        personas = state.personas,
        premiumToneCount = state.premiumToneCount,
        showPremiumCount = !state.isPremium
      )
    }
    if (!state.isPremium) {
      item(key = KEY_UPSELL) {
        NarratorUpsell(
          modifier = horizontalInset,
          onClick = callbacks.onUpsellClick,
          premiumToneCount = state.premiumToneCount
        )
      }
    }
    item(key = KEY_TEMPERATURE) {
      TemperatureSection(
        modifier = horizontalInset,
        isCelsius = state.isCelsius,
        onToggle = callbacks.onTemperatureToggle
      )
    }
    if (state.hasExcludedGenres) {
      item(key = KEY_GENRES) {
        ExcludedGenresSection(
          modifier = horizontalInset,
          genreChips = state.genreChips,
          onGenreRemove = callbacks.onGenreRemove
        )
      }
    }
  }
}

private fun PersonalizationCallbacks.onPersonaTap(persona: PersonaUiState) {
  if (persona.isLocked) onLockedPersonaClick(persona.tone) else onPersonaSelect(persona.tone)
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PersonalizationLoadedContent(
      state = PersonalizationPreviewData.loaded,
      callbacks = PersonalizationCallbacks.Noop
    )
  }
}
