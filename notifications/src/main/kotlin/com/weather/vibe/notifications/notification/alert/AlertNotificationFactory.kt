package com.weather.vibe.notifications.notification.alert

import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.alerts.model.WeatherAlert.HeavyRainImminent
import com.weather.vibe.domain.alerts.model.WeatherAlert.SharpTemperatureDrop
import com.weather.vibe.domain.alerts.model.WeatherAlert.ThunderstormImminent
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.NotificationIds.HEAVY_RAIN
import com.weather.vibe.notifications.notification.NotificationIds.TEMPERATURE_DROP
import com.weather.vibe.notifications.notification.NotificationIds.THUNDERSTORM
import com.weather.vibe.notifications.ui.AlertsResources
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class AlertNotificationFactory(
  private val resources: AlertsResources
) {

  fun create(alert: WeatherAlert): AlertNotification =
    when (alert) {
      is ThunderstormImminent -> thunderstormNotification(alert)
      is HeavyRainImminent -> heavyRainNotification(alert)
      is SharpTemperatureDrop -> temperatureDropNotification(alert)
    }

  private fun thunderstormNotification(alert: ThunderstormImminent): AlertNotification =
    AlertNotification(
      id = THUNDERSTORM,
      title = resources.thunderstormTitle(),
      body = resources.thunderstormBody(alert.expectedAt)
    )

  private fun heavyRainNotification(alert: HeavyRainImminent): AlertNotification =
    AlertNotification(
      id = HEAVY_RAIN,
      title = resources.heavyRainTitle(),
      body = resources.heavyRainBody(
        expectedAt = alert.expectedAt,
        millimetres = alert.millimetres.roundToInt()
      )
    )

  private fun temperatureDropNotification(alert: SharpTemperatureDrop): AlertNotification =
    AlertNotification(
      id = TEMPERATURE_DROP,
      title = resources.temperatureDropTitle(),
      body = resources.temperatureDropBody(
        expectedAt = alert.expectedAt,
        degrees = alert.degreesCelsius.roundToInt()
      )
    )
}
