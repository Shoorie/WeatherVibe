package com.weather.vibe.notifications.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CancellationException

internal class WeatherAlertsWorker(
  context: Context,
  params: WorkerParameters,
  private val checkWeatherAlerts: CheckWeatherAlerts
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result =
    try {
      checkWeatherAlerts()
      Result.success()
    } catch (cancellation: CancellationException) {
      throw cancellation
    } catch (failure: Throwable) {
      Log.w(TAG, "Weather alerts check failed, scheduling retry", failure)
      Result.retry()
    }

  private companion object {
    const val TAG = "WeatherAlertsWorker"
  }
}
