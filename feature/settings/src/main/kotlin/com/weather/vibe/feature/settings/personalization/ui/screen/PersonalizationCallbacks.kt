package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BackClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BriefToneSelect
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.GenreRemove
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.TemperatureUnitToggle

@Immutable
internal data class PersonalizationCallbacks(
  val onBackClick: () -> Unit,
  val onBriefToneSelect: (BriefTone) -> Unit,
  val onGenreRemove: (String) -> Unit,
  val onTemperatureToggle: () -> Unit
) {

  companion object {
    val Noop: PersonalizationCallbacks = PersonalizationCallbacks(
      onBackClick = {},
      onBriefToneSelect = {},
      onGenreRemove = {},
      onTemperatureToggle = {}
    )
  }
}

@Composable
internal fun rememberPersonalizationCallbacks(
  dispatch: (PersonalizationAction) -> Unit
): PersonalizationCallbacks =
  remember(dispatch) {
    PersonalizationCallbacks(
      onBackClick = { dispatch(BackClick) },
      onBriefToneSelect = { tone -> dispatch(BriefToneSelect(tone = tone)) },
      onGenreRemove = { genre -> dispatch(GenreRemove(genre = genre)) },
      onTemperatureToggle = { dispatch(TemperatureUnitToggle) }
    )
  }
