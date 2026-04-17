package com.weather.vibe.scheduling

import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Single
import java.util.concurrent.atomic.AtomicBoolean

@Single
class SchedulingCoordinator internal constructor(
  private val alertsScheduler: SchedulePeriodicWeatherAlerts,
  private val morningBriefScheduler: SchedulePeriodicMorningBrief,
  private val observeUserSettings: ObserveUserSettings
) {

  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  private val started = AtomicBoolean(false)

  fun start() {
    if (!started.compareAndSet(false, true)) return
    observeNotificationPreferences()
      .onEach(::applyPreferences)
      .launchIn(scope)
  }

  private fun observeNotificationPreferences() =
    observeUserSettings()
      .mapNotNull(Result<UserSettings>::getOrNull)
      .map(NotificationPreferences::from)
      .distinctUntilChanged()

  private fun applyPreferences(preferences: NotificationPreferences) {
    applyAlertsPreference(preferences.alertsEnabled)
    applyMorningBriefPreference(preferences.morningBriefEnabled)
  }

  private fun applyAlertsPreference(enabled: Boolean) {
    when (enabled) {
      true -> alertsScheduler.schedule()
      false -> alertsScheduler.cancel()
    }
  }

  private fun applyMorningBriefPreference(enabled: Boolean) {
    when (enabled) {
      true -> morningBriefScheduler.schedule()
      false -> morningBriefScheduler.cancel()
    }
  }
}
