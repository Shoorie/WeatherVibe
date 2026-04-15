package com.weather.vibe.feature.widget.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CancellationException

internal class WidgetRefreshWorker(
  context: Context,
  params: WorkerParameters,
  private val refreshWidget: RefreshCurrentLocationWidget
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result =
    try {
      Log.d(TAG, "doWork start")
      refreshWidget()
      Log.d(TAG, "doWork success")
      Result.success()
    } catch (cancellation: CancellationException) {
      throw cancellation
    } catch (error: Throwable) {
      Log.w(TAG, "doWork failed — will retry", error)
      Result.retry()
    }

  private companion object {
    const val TAG = "WidgetRefresh"
  }
}
