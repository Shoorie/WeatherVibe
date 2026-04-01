package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
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

  fun create(settings: UserSettings): SettingsUiState.Loaded =
    SettingsUiState.Loaded(
      briefToneOptions = createBriefToneOptions(selected = settings.briefTone),
      genreChips = createExcludedGenreChips(excluded = settings.excludedGenres),
      hasExcludedGenres = settings.excludedGenres.isNotEmpty(),
      isCelsius = settings.temperatureUnit == CELSIUS
    )

  private fun createBriefToneOptions(selected: BriefTone): List<BriefToneOptionUiState> =
    listOf(
      BriefToneOptionUiState(
        description = resources.briefToneWittyDescription(),
        isSelected = selected == WITTY_AND_FRIENDLY,
        label = resources.briefToneWittyLabel(),
        tone = WITTY_AND_FRIENDLY
      ),
      BriefToneOptionUiState(
        description = resources.briefToneFormalDescription(),
        isSelected = selected == FORMAL,
        label = resources.briefToneFormalLabel(),
        tone = FORMAL
      ),
      BriefToneOptionUiState(
        description = resources.briefToneHumorousDescription(),
        isSelected = selected == HUMOROUS,
        label = resources.briefToneHumorousLabel(),
        tone = HUMOROUS
      )
    )

  private fun createExcludedGenreChips(
    excluded: Set<String>
  ): List<GenreChipSettingsUiState> =
    excluded.sorted().map { genre ->
      GenreChipSettingsUiState(name = genre)
    }
}
