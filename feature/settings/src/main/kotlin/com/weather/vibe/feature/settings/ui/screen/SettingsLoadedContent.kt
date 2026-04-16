package com.weather.vibe.feature.settings.ui.screen

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
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.briefToneOptions
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.genreChips
import com.weather.vibe.feature.settings.ui.SettingsKeys.KEY_ALERTS
import com.weather.vibe.feature.settings.ui.SettingsKeys.KEY_BRIEF_TONE
import com.weather.vibe.feature.settings.ui.SettingsKeys.KEY_GENRES
import com.weather.vibe.feature.settings.ui.SettingsKeys.KEY_MORNING_BRIEF
import com.weather.vibe.feature.settings.ui.SettingsKeys.KEY_TEMPERATURE
import com.weather.vibe.feature.settings.ui.component.alerts.AlertsSection
import com.weather.vibe.feature.settings.ui.component.brieftone.BriefToneSection
import com.weather.vibe.feature.settings.ui.component.genres.ExcludedGenresSection
import com.weather.vibe.feature.settings.ui.component.morningbrief.MorningBriefSection
import com.weather.vibe.feature.settings.ui.component.temperature.TemperatureSection

@Composable
internal fun SettingsLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  callbacks: SettingsCallbacks
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
    item(key = KEY_MORNING_BRIEF) {
      MorningBriefSection(
        enabled = state.morningBriefEnabled,
        onToggle = callbacks.onMorningBriefToggle
      )
    }
    item(key = KEY_ALERTS) {
      AlertsSection(
        enabled = state.alertsEnabled,
        onToggle = callbacks.onAlertsToggle
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
    SettingsLoadedContent(
      state = Loaded(
        alertsEnabled = true,
        briefToneOptions = briefToneOptions,
        genreChips = genreChips,
        hasExcludedGenres = true,
        isCelsius = true,
        morningBriefEnabled = true
      ),
      callbacks = SettingsCallbacks.Noop
    )
  }
}
