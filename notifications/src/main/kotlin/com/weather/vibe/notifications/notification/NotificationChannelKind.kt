package com.weather.vibe.notifications.notification

import androidx.annotation.StringRes
import com.weather.vibe.notifications.R

enum class NotificationChannelKind(
  val channelId: String,
  @StringRes val nameRes: Int,
  @StringRes val descriptionRes: Int
) {
  WEATHER_ALERTS(
    channelId = "weather_vibe_alerts",
    nameRes = R.string.alerts_channel_weather_alerts_name,
    descriptionRes = R.string.alerts_channel_weather_alerts_description
  ),
  POLLEN_ALERTS(
    channelId = "weather_vibe_pollen_alerts",
    nameRes = R.string.alerts_channel_pollen_alerts_name,
    descriptionRes = R.string.alerts_channel_pollen_alerts_description
  ),
  MORNING_BRIEF(
    channelId = "weather_vibe_morning_brief",
    nameRes = R.string.alerts_channel_morning_brief_name,
    descriptionRes = R.string.alerts_channel_morning_brief_description
  ),
  MOOD_REMINDER(
    channelId = "weather_vibe_mood_reminder",
    nameRes = R.string.alerts_channel_mood_reminder_name,
    descriptionRes = R.string.alerts_channel_mood_reminder_description
  )
}
