package com.weather.vibe.notifications.notification.alert

import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.alerts.model.WeatherAlert.HeavyRainImminent
import com.weather.vibe.domain.alerts.model.WeatherAlert.HighPollen
import com.weather.vibe.domain.alerts.model.WeatherAlert.HighUvIndex
import com.weather.vibe.domain.alerts.model.WeatherAlert.PoorAirQuality
import com.weather.vibe.domain.alerts.model.WeatherAlert.SharpTemperatureDrop
import com.weather.vibe.domain.alerts.model.WeatherAlert.ThunderstormImminent
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.NotificationChannelKind.POLLEN_ALERTS
import com.weather.vibe.notifications.notification.NotificationChannelKind.WEATHER_ALERTS
import com.weather.vibe.notifications.notification.NotificationIds.HEAVY_RAIN
import com.weather.vibe.notifications.notification.NotificationIds.HIGH_POLLEN
import com.weather.vibe.notifications.notification.NotificationIds.HIGH_UV_INDEX
import com.weather.vibe.notifications.notification.NotificationIds.POOR_AIR_QUALITY
import com.weather.vibe.notifications.notification.NotificationIds.TEMPERATURE_DROP
import com.weather.vibe.notifications.notification.NotificationIds.THUNDERSTORM
import com.weather.vibe.notifications.ui.AlertsResources
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
class AlertNotificationFactory internal constructor(
  private val resources: AlertsResources
) {

  fun create(alert: WeatherAlert): AlertNotification =
    when (alert) {
      is ThunderstormImminent -> thunderstormNotification(alert)
      is HeavyRainImminent -> heavyRainNotification(alert)
      is SharpTemperatureDrop -> temperatureDropNotification(alert)
      is PoorAirQuality -> airQualityNotification(alert)
      is HighPollen -> pollenNotification(alert)
      is HighUvIndex -> uvNotification(alert)
    }

  private fun thunderstormNotification(alert: ThunderstormImminent): AlertNotification =
    AlertNotification(
      body = resources.thunderstormBody(alert.expectedAt),
      id = THUNDERSTORM,
      kind = WEATHER_ALERTS,
      title = resources.thunderstormTitle()
    )

  private fun heavyRainNotification(alert: HeavyRainImminent): AlertNotification =
    AlertNotification(
      body = resources.heavyRainBody(
        expectedAt = alert.expectedAt,
        millimetres = alert.millimetres.roundToInt()
      ),
      id = HEAVY_RAIN,
      kind = WEATHER_ALERTS,
      title = resources.heavyRainTitle()
    )

  private fun temperatureDropNotification(alert: SharpTemperatureDrop): AlertNotification =
    AlertNotification(
      body = resources.temperatureDropBody(
        expectedAt = alert.expectedAt,
        degrees = alert.degreesCelsius.roundToInt()
      ),
      id = TEMPERATURE_DROP,
      kind = WEATHER_ALERTS,
      title = resources.temperatureDropTitle()
    )

  private fun airQualityNotification(alert: PoorAirQuality): AlertNotification =
    AlertNotification(
      body = resources.airQualityBody(
        europeanAqi = alert.europeanAqi,
        levelLabel = resources.aqiLevelLabel(alert.level)
      ),
      id = POOR_AIR_QUALITY,
      kind = WEATHER_ALERTS,
      title = resources.airQualityTitle()
    )

  private fun pollenNotification(alert: HighPollen): AlertNotification =
    AlertNotification(
      body = resources.pollenBody(alert.species),
      id = HIGH_POLLEN,
      kind = POLLEN_ALERTS,
      title = resources.pollenTitle()
    )

  private fun uvNotification(alert: HighUvIndex): AlertNotification =
    AlertNotification(
      body = resources.uvAlertBody(uvIndex = alert.uvIndex, level = alert.level),
      id = HIGH_UV_INDEX,
      kind = WEATHER_ALERTS,
      title = resources.uvAlertTitle()
    )
}
