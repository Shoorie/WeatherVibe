package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BackClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BuyPremiumClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.GenreRemove
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.LockedPersonaClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.PaywallDismiss
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.PersonaSelect
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.ToneUnlockedViaAd
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.UpsellClick

@Immutable
internal data class PersonalizationCallbacks(
  val onBackClick: () -> Unit,
  val onBuyPremium: () -> Unit,
  val onGenreRemove: (String) -> Unit,
  val onLockedPersonaClick: (BriefTone) -> Unit,
  val onPaywallDismiss: () -> Unit,
  val onPersonaSelect: (BriefTone) -> Unit,
  val onTemperatureToggle: () -> Unit,
  val onToneUnlockedViaAd: (BriefTone) -> Unit,
  val onUpsellClick: () -> Unit
) {

  companion object {
    val Noop: PersonalizationCallbacks = PersonalizationCallbacks(
      onBackClick = {},
      onBuyPremium = {},
      onGenreRemove = {},
      onLockedPersonaClick = {},
      onPaywallDismiss = {},
      onPersonaSelect = {},
      onTemperatureToggle = {},
      onToneUnlockedViaAd = {},
      onUpsellClick = {}
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
      onBuyPremium = { dispatch(BuyPremiumClick) },
      onGenreRemove = { genre -> dispatch(GenreRemove(genre = genre)) },
      onLockedPersonaClick = { tone -> dispatch(LockedPersonaClick(tone = tone)) },
      onPaywallDismiss = { dispatch(PaywallDismiss) },
      onPersonaSelect = { tone -> dispatch(PersonaSelect(tone = tone)) },
      onTemperatureToggle = { dispatch(TemperatureUnitToggle) },
      onToneUnlockedViaAd = { tone -> dispatch(ToneUnlockedViaAd(tone = tone)) },
      onUpsellClick = { dispatch(UpsellClick) }
    )
  }
