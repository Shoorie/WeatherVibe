package com.weather.vibe.scheduling

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weather.vibe.scheduling.work.runDelivery
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class WeatherAlertsWorker(
  context: Context,
  params: WorkerParameters,
  private val checkWeatherAlerts: CheckWeatherAlerts
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result =
    runDelivery(tag = TAG) { checkWeatherAlerts() }

  private companion object {
    const val TAG = "WeatherAlertsWorker"
  }
}
