package com.weather.vibe.notifications.notification.mood

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.weather.vibe.notifications.R
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.NotificationChannelKind.MOOD_REMINDER
import com.weather.vibe.notifications.notification.NotificationIds
import com.weather.vibe.notifications.notification.NotificationIds.MOOD_REMINDER_PICK_REQUEST_BASE
import com.weather.vibe.notifications.notification.mood.MoodReminderActions.ACTION_PICK
import com.weather.vibe.notifications.notification.mood.MoodReminderActions.EXTRA_RATING
import com.weather.vibe.notifications.ui.MoodReminderResources
import org.koin.core.annotation.Factory
import java.time.DayOfWeek

@Factory
class MoodReminderNotificationFactory internal constructor(
  private val context: Context,
  private val resources: MoodReminderResources
) {

  fun createPrompt(
    username: String?,
    dayOfWeek: DayOfWeek,
    receiverClass: Class<*>
  ): AlertNotification {
    val title = resources.greetingTitle(username = username, dayOfWeek = dayOfWeek)
    return AlertNotification(
      body = resources.body(),
      collapsedRemoteViews = buildRatingView(
        layoutRes = R.layout.notification_mood_reminder_collapsed,
        receiverClass = receiverClass,
        title = title,
        includeBody = false
      ),
      expandedRemoteViews = buildRatingView(
        layoutRes = R.layout.notification_mood_reminder_expanded,
        receiverClass = receiverClass,
        title = title,
        includeBody = true
      ),
      id = NotificationIds.MOOD_REMINDER,
      kind = MOOD_REMINDER,
      title = title
    )
  }

  fun createConfirmation(): AlertNotification =
    AlertNotification(
      autoCancelMillis = CONFIRMATION_TIMEOUT_MS,
      body = resources.loggedBody(),
      id = NotificationIds.MOOD_REMINDER,
      kind = MOOD_REMINDER,
      title = resources.loggedTitle()
    )

  private fun buildRatingView(
    @LayoutRes layoutRes: Int,
    receiverClass: Class<*>,
    title: String,
    includeBody: Boolean
  ): RemoteViews {
    val views = RemoteViews(context.packageName, layoutRes)
    views.setTextViewText(R.id.mood_title, title)
    if (includeBody) {
      views.setTextViewText(R.id.mood_body, resources.body())
    }
    EMOJI_BUTTON_IDS.forEachIndexed { index, viewId ->
      val rating = index + RATING_MIN
      views.setTextViewText(viewId, resources.emojiForRating(rating))
      views.setOnClickPendingIntent(
        viewId,
        pickIntent(rating = rating, receiverClass = receiverClass)
      )
    }
    return views
  }

  private fun pickIntent(rating: Int, receiverClass: Class<*>): PendingIntent {
    val intent = Intent(ACTION_PICK)
      .setClass(context, receiverClass)
      .putExtra(EXTRA_RATING, rating)
    return PendingIntent.getBroadcast(
      context,
      MOOD_REMINDER_PICK_REQUEST_BASE + rating,
      intent,
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )
  }

  private companion object {
    const val RATING_MIN = 1
    const val CONFIRMATION_TIMEOUT_MS = 2_500L

    @IdRes
    val EMOJI_BUTTON_IDS: List<Int> = listOf(
      R.id.mood_rating_1,
      R.id.mood_rating_2,
      R.id.mood_rating_3,
      R.id.mood_rating_4,
      R.id.mood_rating_5
    )
  }
}
