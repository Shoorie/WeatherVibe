package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.domain.settings.model.BriefTone

internal sealed interface PersonalizationAction {
  data object BackClick : PersonalizationAction
  data class BriefToneSelect(val tone: BriefTone) : PersonalizationAction
  data class GenreRemove(val genre: String) : PersonalizationAction
  data object TemperatureUnitToggle : PersonalizationAction
}
