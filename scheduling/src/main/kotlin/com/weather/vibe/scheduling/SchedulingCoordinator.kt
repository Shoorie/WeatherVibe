package com.weather.vibe.scheduling

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.scheduling.work.MoodReminderWorkSpec
import com.weather.vibe.scheduling.work.MorningBriefWorkSpec
import com.weather.vibe.scheduling.work.NotificationScheduler
import com.weather.vibe.scheduling.work.NotificationWorkSpec
import com.weather.vibe.scheduling.work.PollenAlertsWorkSpec
import com.weather.vibe.scheduling.work.WeatherAlertsWorkSpec
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
  private val observeUserSettings: ObserveUserSettings,
  private val scheduler: NotificationScheduler,
  private val timeProvider: TimeProvider
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
    apply(
      spec = MorningBriefWorkSpec.create(timeProvider = timeProvider),
      enabled = preferences.morningBriefEnabled
    )
    apply(
      spec = WeatherAlertsWorkSpec.create(),
      enabled = preferences.weatherAlertsEnabled
    )
    apply(
      spec = PollenAlertsWorkSpec.create(timeProvider = timeProvider),
      enabled = preferences.pollenAlertsEnabled
    )
    apply(
      spec = MoodReminderWorkSpec.create(timeProvider = timeProvider),
      enabled = preferences.moodReminderEnabled
    )
  }

  private fun apply(spec: NotificationWorkSpec, enabled: Boolean) {
    when (enabled) {
      true -> scheduler.schedule(spec)
      false -> scheduler.cancel(spec.workerName)
    }
  }
}
