package com.weather.vibe.scheduling

import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.alerts.usecase.GatherWeatherAlerts
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.alert.AlertNotificationFactory
import org.koin.core.annotation.Factory

@Factory
internal class CheckWeatherAlerts(
  private val gatherWeatherAlerts: GatherWeatherAlerts,
  private val notificationFactory: AlertNotificationFactory,
  private val notifier: AlertNotifier
) {

  suspend operator fun invoke() {
    gatherWeatherAlerts().forEach(::notify)
  }

  private fun notify(alert: WeatherAlert) {
    notifier.post(notificationFactory.create(alert))
  }
}
