package com.weather.vibe.notifications.ui

import android.content.Context
import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.FAIR
import com.weather.vibe.domain.airquality.model.AqiLevel.GOOD
import com.weather.vibe.domain.airquality.model.AqiLevel.MODERATE
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.VERY_POOR
import com.weather.vibe.notifications.R
import com.weather.vibe.notifications.notification.AlertsTimeFormatter
import org.koin.core.annotation.Factory
import java.time.LocalDateTime

@Factory
internal class AlertsResources(
  private val context: Context,
  private val timeFormatter: AlertsTimeFormatter
) {

  fun channelName(): String =
    context.getString(R.string.alerts_channel_name)

  fun channelDescription(): String =
    context.getString(R.string.alerts_channel_description)

  fun thunderstormTitle(): String =
    context.getString(R.string.alerts_thunderstorm_title)

  fun thunderstormBody(expectedAt: LocalDateTime): String =
    context.getString(R.string.alerts_thunderstorm_body, timeFormatter.format(expectedAt))

  fun heavyRainTitle(): String =
    context.getString(R.string.alerts_heavy_rain_title)

  fun heavyRainBody(expectedAt: LocalDateTime, millimetres: Int): String =
    context.getString(
      R.string.alerts_heavy_rain_body,
      timeFormatter.format(expectedAt),
      millimetres
    )

  fun temperatureDropTitle(): String =
    context.getString(R.string.alerts_temperature_drop_title)

  fun temperatureDropBody(expectedAt: LocalDateTime, degrees: Int): String =
    context.getString(
      R.string.alerts_temperature_drop_body,
      degrees,
      timeFormatter.format(expectedAt)
    )

  fun airQualityTitle(): String =
    context.getString(R.string.alerts_air_quality_title)

  fun airQualityBody(europeanAqi: Int, levelLabel: String): String =
    context.getString(R.string.alerts_air_quality_body, levelLabel, europeanAqi)

  fun aqiLevelLabel(level: AqiLevel): String =
    context.getString(level.labelRes())

  private fun AqiLevel.labelRes(): Int = when (this) {
    GOOD -> R.string.alerts_aqi_level_good
    FAIR -> R.string.alerts_aqi_level_fair
    MODERATE -> R.string.alerts_aqi_level_moderate
    POOR -> R.string.alerts_aqi_level_poor
    VERY_POOR -> R.string.alerts_aqi_level_very_poor
    EXTREMELY_POOR -> R.string.alerts_aqi_level_extremely_poor
  }
}
