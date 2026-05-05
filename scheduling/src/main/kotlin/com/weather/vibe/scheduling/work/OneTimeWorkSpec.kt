package com.weather.vibe.scheduling.work

import androidx.work.CoroutineWorker
import java.time.Duration

internal data class OneTimeWorkSpec(
  override val backoff: Duration,
  val nextDelay: () -> Duration,
  override val workerClass: Class<out CoroutineWorker>,
  override val workerName: String
) : NotificationWorkSpec
