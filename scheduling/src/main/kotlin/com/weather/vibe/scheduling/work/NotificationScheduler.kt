package com.weather.vibe.scheduling.work

import androidx.work.BackoffPolicy.EXPONENTIAL
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.NetworkType.CONNECTED
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import org.koin.core.annotation.Single
import java.util.concurrent.TimeUnit.MILLISECONDS

@Single
internal class NotificationScheduler(
  private val workManager: WorkManager
) {

  fun schedule(spec: NotificationWorkSpec) {
    when (spec) {
      is OneTimeWorkSpec -> scheduleOneTime(spec)
      is PeriodicWorkSpec -> schedulePeriodic(spec)
    }
  }

  fun cancel(workerName: String) {
    workManager.cancelUniqueWork(workerName)
  }

  private fun scheduleOneTime(spec: OneTimeWorkSpec) {
    val request = OneTimeWorkRequest.Builder(spec.workerClass)
      .setConstraints(connectedConstraints())
      .setBackoffCriteria(EXPONENTIAL, spec.backoff.toMillis(), MILLISECONDS)
      .setInitialDelay(spec.nextDelay().toMillis(), MILLISECONDS)
      .build()
    workManager.enqueueUniqueWork(spec.workerName, REPLACE, request)
  }

  private fun schedulePeriodic(spec: PeriodicWorkSpec) {
    val builder = PeriodicWorkRequest.Builder(
      spec.workerClass,
      spec.repeatInterval.toMillis(),
      MILLISECONDS
    )
      .setConstraints(connectedConstraints())
      .setBackoffCriteria(EXPONENTIAL, spec.backoff.toMillis(), MILLISECONDS)

    spec.initialDelay()?.let { delay ->
      builder.setInitialDelay(delay.toMillis(), MILLISECONDS)
    }

    workManager.enqueueUniquePeriodicWork(
      spec.workerName,
      ExistingPeriodicWorkPolicy.UPDATE,
      builder.build()
    )
  }

  private fun connectedConstraints(): Constraints =
    Constraints.Builder()
      .setRequiredNetworkType(CONNECTED)
      .build()
}
