package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.domain.settings.model.BriefTone

internal sealed interface PersonalizationAction {
  data object BackClick : PersonalizationAction
  data object BuyPremiumClick : PersonalizationAction
  data class GenreRemove(val genre: String) : PersonalizationAction
  data class LockedPersonaClick(val tone: BriefTone) : PersonalizationAction
  data object PaywallDismiss : PersonalizationAction
  data class PersonaSelect(val tone: BriefTone) : PersonalizationAction
  data object TemperatureUnitToggle : PersonalizationAction
  data class ToneUnlockedViaAd(val tone: BriefTone) : PersonalizationAction
  data object UpsellClick : PersonalizationAction
}
