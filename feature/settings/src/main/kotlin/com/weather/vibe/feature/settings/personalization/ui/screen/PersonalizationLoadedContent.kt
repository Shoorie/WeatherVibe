package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreviewData
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_BRIEF_TONE
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_GENRES
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationKeys.KEY_TEMPERATURE
import com.weather.vibe.feature.settings.personalization.ui.component.brieftone.BriefToneSection
import com.weather.vibe.feature.settings.personalization.ui.component.genres.ExcludedGenresSection
import com.weather.vibe.feature.settings.personalization.ui.component.temperature.TemperatureSection

@Composable
internal fun PersonalizationLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  callbacks: PersonalizationCallbacks
) {

  val contentPadding = remember {
    PaddingValues(
      start = Medium,
      end = Medium,
      top = Medium,
      bottom = ExtraLarge
    )
  }

  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    item(key = KEY_BRIEF_TONE) {
      BriefToneSection(
        briefToneOptions = state.briefToneOptions,
        onBriefToneSelect = callbacks.onBriefToneSelect
      )
    }
    item(key = KEY_TEMPERATURE) {
      TemperatureSection(
        isCelsius = state.isCelsius,
        onToggle = callbacks.onTemperatureToggle
      )
    }
    if (state.hasExcludedGenres) {
      item(key = KEY_GENRES) {
        ExcludedGenresSection(
          genreChips = state.genreChips,
          onGenreRemove = callbacks.onGenreRemove
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PersonalizationLoadedContent(
      state = Loaded(
        briefToneOptions = PersonalizationPreviewData.briefToneOptions,
        genreChips = PersonalizationPreviewData.genreChips,
        hasExcludedGenres = true,
        isCelsius = true
      ),
      callbacks = PersonalizationCallbacks.Noop
    )
  }
}
