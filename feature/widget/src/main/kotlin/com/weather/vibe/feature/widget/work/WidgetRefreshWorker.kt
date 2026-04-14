package com.weather.vibe.feature.widget.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weather.vibe.domain.widget.usecase.ObservePinnedWidgets
import com.weather.vibe.domain.widget.usecase.RefreshWidgetSnapshot
import kotlinx.coroutines.flow.first
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class WidgetRefreshWorker(
  context: Context,
  params: WorkerParameters,
  private val observePinnedWidgets: ObservePinnedWidgets,
  private val refreshWidgetSnapshot: RefreshWidgetSnapshot,
  private val widgetUpdater: WidgetUpdater
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    val pinned = observePinnedWidgets().first()
    pinned.values.forEach { location -> refreshWidgetSnapshot(location) }
    widgetUpdater.updateAll()
    return Result.success()
  }
}
