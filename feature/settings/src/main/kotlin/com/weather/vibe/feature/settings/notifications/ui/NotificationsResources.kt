package com.weather.vibe.feature.settings.notifications.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.settings.R
import org.koin.core.annotation.Factory

@Factory
internal class NotificationsResources(private val context: Context) {

  fun defaultError(): String =
    context.getString(R.string.notifications_error_default)

  object Emojis {
    fun error(): String = "⚡"
    fun moodReminder(): String = "🌙"
    fun morningBrief(): String = "🌅"
    fun pollenAlerts(): String = "🌿"
    fun weatherAlerts(): String = "⚠️"
  }

  object Texts {

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.notifications_screen_title)

    @Composable
    fun screenSubtitle(): String =
      stringResource(R.string.notifications_screen_subtitle)

    @Composable
    fun weatherAlertsSection(): String =
      stringResource(R.string.notifications_section_weather_alerts)

    @Composable
    fun weatherAlertsSectionSubtitle(): String =
      stringResource(R.string.notifications_section_weather_alerts_subtitle)

    @Composable
    fun pollenAlertsSection(): String =
      stringResource(R.string.notifications_section_pollen_alerts)

    @Composable
    fun pollenAlertsSectionSubtitle(): String =
      stringResource(R.string.notifications_section_pollen_alerts_subtitle)

    @Composable
    fun morningBriefSection(): String =
      stringResource(R.string.notifications_section_morning_brief)

    @Composable
    fun morningBriefSectionSubtitle(): String =
      stringResource(R.string.notifications_section_morning_brief_subtitle)

    @Composable
    fun moodReminderSection(): String =
      stringResource(R.string.notifications_section_mood_reminder)

    @Composable
    fun moodReminderSectionSubtitle(): String =
      stringResource(R.string.notifications_section_mood_reminder_subtitle)

    @Composable
    fun toggleOn(): String =
      stringResource(R.string.notifications_toggle_on)

    @Composable
    fun toggleOff(): String =
      stringResource(R.string.notifications_toggle_off)

    @Composable
    fun errorTitle(): String =
      stringResource(R.string.notifications_error_title)
  }
}
