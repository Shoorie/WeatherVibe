package com.weather.vibe.scheduling.work

import androidx.work.CoroutineWorker
import java.time.Duration

internal data class PeriodicWorkSpec(
  override val backoff: Duration,
  val initialDelay: () -> Duration?,
  val repeatInterval: Duration,
  override val workerClass: Class<out CoroutineWorker>,
  override val workerName: String
) : NotificationWorkSpec
