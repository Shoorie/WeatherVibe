package com.weather.vibe.feature.widget.work

import android.content.Context
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
      refreshWidget()
      Result.success()
    } catch (cancellation: CancellationException) {
      throw cancellation
    } catch (_: Throwable) {
      Result.retry()
    }
}
