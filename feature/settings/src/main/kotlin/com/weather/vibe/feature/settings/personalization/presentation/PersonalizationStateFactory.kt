package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.personalization.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.GenreChipUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory

@Factory
internal class PersonalizationStateFactory(
  private val resources: PersonalizationResources
) {

  fun initial(): Loaded =
    Loaded(
      briefToneOptions = persistentListOf(),
      genreChips = persistentListOf(),
      hasExcludedGenres = false,
      isCelsius = true
    )

  fun create(
    availableTones: List<BriefTone>,
    settings: UserSettings
  ): Loaded =
    Loaded(
      briefToneOptions = createBriefToneOptions(
        availableTones = availableTones,
        selected = settings.briefTone
      ),
      genreChips = createExcludedGenreChips(excluded = settings.excludedGenres),
      hasExcludedGenres = settings.excludedGenres.isNotEmpty(),
      isCelsius = settings.temperatureUnit == CELSIUS
    )

  fun createError(): Error =
    Error(message = resources.defaultError())

  private fun createBriefToneOptions(
    availableTones: List<BriefTone>,
    selected: BriefTone
  ): ImmutableList<BriefToneOptionUiState> =
    availableTones.map { tone ->
      BriefToneOptionUiState(
        description = resources.briefToneDescription(tone),
        isSelected = tone == selected,
        label = resources.briefToneLabel(tone),
        tone = tone
      )
    }.toImmutableList()

  private fun createExcludedGenreChips(
    excluded: Set<String>
  ): ImmutableList<GenreChipUiState> =
    excluded.sorted().map { genre ->
      GenreChipUiState(name = genre)
    }.toImmutableList()
}
