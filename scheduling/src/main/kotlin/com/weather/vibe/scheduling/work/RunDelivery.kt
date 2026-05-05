package com.weather.vibe.scheduling.work

import android.util.Log
import androidx.work.ListenableWorker
import com.weather.vibe.core.coroutines.suspendRunCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal suspend fun runDelivery(
  tag: String,
  block: suspend () -> Unit
): ListenableWorker.Result =
  withContext(Dispatchers.IO) {
    suspendRunCatching { block() }.fold(
      onSuccess = { ListenableWorker.Result.success() },
      onFailure = { failure ->
        Log.w(tag, "Delivery failed, scheduling retry", failure)
        ListenableWorker.Result.retry()
      }
    )
  }

internal suspend fun runDailyDelivery(
  tag: String,
  block: suspend () -> Unit
): ListenableWorker.Result =
  withContext(Dispatchers.IO) {
    suspendRunCatching { block() }.onFailure { failure ->
      Log.w(tag, "Daily delivery failed, will retry on next slot", failure)
    }
    ListenableWorker.Result.success()
  }
