package com.weather.vibe.scheduling.work

import androidx.work.CoroutineWorker
import java.time.Duration

internal sealed interface NotificationWorkSpec {

  val backoff: Duration
  val workerClass: Class<out CoroutineWorker>
  val workerName: String
}
