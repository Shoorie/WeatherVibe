package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.model.WidgetPromoOutcome
import com.weather.vibe.domain.settings.model.WidgetPromoOutcome.Reveal
import com.weather.vibe.domain.settings.model.WidgetPromoOutcome.Skip
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class ResolveWidgetPromo internal constructor(
  private val markWidgetPromoEligible: MarkWidgetPromoEligible,
  private val observeUserSettings: ObserveUserSettings
) {

  suspend operator fun invoke(): WidgetPromoOutcome {

    val settings = observeUserSettings()
      .first().getOrNull()
      ?: return Skip

    return settings.toOutcome()
  }

  private suspend fun UserSettings.toOutcome(): WidgetPromoOutcome =
    when {
      !welcomeOnboardingSeen -> Skip
      widgetPromoSeen -> Skip
      !widgetPromoEligible -> deferToNextSession()
      else -> Reveal
    }

  private suspend fun deferToNextSession(): WidgetPromoOutcome {
    markWidgetPromoEligible()
    return Skip
  }
}
