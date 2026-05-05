package com.weather.vibe.scheduling

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.profile.usecase.ObserveProfile
import com.weather.vibe.domain.viberating.usecase.ObserveTodayEntries
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.mood.MoodReminderNotificationFactory
import com.weather.vibe.scheduling.notification.MoodPickReceiver
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.annotation.Factory

@Factory
internal class DeliverMoodReminder(
  private val notificationFactory: MoodReminderNotificationFactory,
  private val notifier: AlertNotifier,
  private val observeProfile: ObserveProfile,
  private val observeTodayEntries: ObserveTodayEntries,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(force: Boolean = false) {
    if (!force && hasTodayEntries()) return
    notifier.post(buildPrompt())
  }

  private suspend fun hasTodayEntries(): Boolean =
    !observeTodayEntries().firstOrNull().isNullOrEmpty()

  private suspend fun buildPrompt(): AlertNotification =
    notificationFactory.createPrompt(
      dayOfWeek = timeProvider.now().dayOfWeek,
      receiverClass = MoodPickReceiver::class.java,
      username = currentUsername()
    )

  private suspend fun currentUsername(): String? =
    observeProfile().firstOrNull()?.username?.takeIf(String::isNotBlank)
}
