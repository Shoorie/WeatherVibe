package com.weather.vibe.notifications.work

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CancellationException
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class MorningBriefWorker(
  context: Context,
  params: WorkerParameters,
  private val deliverMorningBrief: DeliverMorningBrief
) : CoroutineWorker(context, params) {

  @RequiresPermission(POST_NOTIFICATIONS)
  override suspend fun doWork(): Result =
    try {
      deliverMorningBrief()
      Result.success()
    } catch (cancellation: CancellationException) {
      throw cancellation
    } catch (failure: Throwable) {
      Log.w(TAG, "Morning brief delivery failed, scheduling retry", failure)
      Result.retry()
    }

  private companion object {
    const val TAG = "MorningBriefWorker"
  }
}
