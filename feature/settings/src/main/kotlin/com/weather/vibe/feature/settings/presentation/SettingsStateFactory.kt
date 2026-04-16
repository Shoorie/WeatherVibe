package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.presentation.state.GenreChipSettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.ui.SettingsResources
import org.koin.core.annotation.Factory

@Factory
internal class SettingsStateFactory(
  private val resources: SettingsResources
) {

  fun create(
    availableTones: List<BriefTone>,
    settings: UserSettings
  ): SettingsUiState.Loaded =
    SettingsUiState.Loaded(
      alertsEnabled = settings.alertsEnabled,
      briefToneOptions = createBriefToneOptions(
        availableTones = availableTones,
        selected = settings.briefTone
      ),
      genreChips = createExcludedGenreChips(excluded = settings.excludedGenres),
      hasExcludedGenres = settings.excludedGenres.isNotEmpty(),
      isCelsius = settings.temperatureUnit == CELSIUS,
      morningBriefEnabled = settings.morningBriefEnabled
    )

  private fun createBriefToneOptions(
    availableTones: List<BriefTone>,
    selected: BriefTone
  ): List<BriefToneOptionUiState> =
    availableTones.map { tone ->
      BriefToneOptionUiState(
        description = resources.briefToneDescription(tone),
        isSelected = tone == selected,
        label = resources.briefToneLabel(tone),
        tone = tone
      )
    }

  private fun createExcludedGenreChips(
    excluded: Set<String>
  ): List<GenreChipSettingsUiState> =
    excluded.sorted().map { genre ->
      GenreChipSettingsUiState(name = genre)
    }
}
