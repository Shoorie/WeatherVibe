package com.weather.vibe.notifications.work

import android.Manifest.permission.POST_NOTIFICATIONS
import androidx.annotation.RequiresPermission
import com.weather.vibe.domain.alerts.usecase.GetMorningBriefText
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.brief.MorningBriefNotificationFactory
import org.koin.core.annotation.Factory

@Factory
internal class DeliverMorningBrief(
  private val getMorningBriefText: GetMorningBriefText,
  private val notificationFactory: MorningBriefNotificationFactory,
  private val notifier: AlertNotifier
) {

  @RequiresPermission(POST_NOTIFICATIONS)
  suspend operator fun invoke() {
    val briefText = getMorningBriefText() ?: return
    notifier.post(notificationFactory.create(briefText))
  }
}
