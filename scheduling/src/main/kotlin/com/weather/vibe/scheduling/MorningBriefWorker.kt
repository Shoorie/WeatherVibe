package com.weather.vibe.scheduling

import android.content.Context
import android.util.Log
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
