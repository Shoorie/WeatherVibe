package com.weather.vibe.scheduling

import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.alerts.usecase.GatherPollenAlerts
import com.weather.vibe.domain.settings.usecase.ArePollenAlertsEnabled
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.alert.AlertNotificationFactory
import org.koin.core.annotation.Factory

@Factory
internal class CheckPollenAlerts(
  private val arePollenAlertsEnabled: ArePollenAlertsEnabled,
  private val gatherPollenAlerts: GatherPollenAlerts,
  private val notificationFactory: AlertNotificationFactory,
  private val notifier: AlertNotifier
) {

  suspend operator fun invoke() {
    if (!arePollenAlertsEnabled()) return
    gatherPollenAlerts().forEach(::notify)
  }

  private fun notify(alert: WeatherAlert) {
    notifier.post(notificationFactory.create(alert))
  }
}
