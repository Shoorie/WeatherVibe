package com.weather.vibe.feature.widget.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.widget.usecase.RefreshWidgetSnapshot
import kotlinx.coroutines.flow.first
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class WidgetRefreshWorker(
  context: Context,
  params: WorkerParameters,
  private val getRecentLocations: GetRecentLocations,
  private val refreshWidgetSnapshot: RefreshWidgetSnapshot,
  private val widgetUpdater: WidgetUpdater
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    val location = getRecentLocations().first().getOrNull()?.firstOrNull()
      ?: return Result.success()

    val refresh = refreshWidgetSnapshot(location)
    widgetUpdater.updateAll()

    return when {
      refresh.isSuccess -> Result.success()
      else -> Result.retry()
    }
  }
}
