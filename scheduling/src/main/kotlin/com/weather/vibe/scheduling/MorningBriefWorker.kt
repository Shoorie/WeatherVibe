package com.weather.vibe.scheduling

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weather.vibe.scheduling.work.DailyWorkRescheduler
import com.weather.vibe.scheduling.work.runDailyDelivery
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class MorningBriefWorker(
  context: Context,
  params: WorkerParameters,
  private val deliverMorningBrief: DeliverMorningBrief,
  private val rescheduler: DailyWorkRescheduler
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    val result = runDailyDelivery(tag = TAG) { deliverMorningBrief() }
    rescheduler.rescheduleMorningBrief()
    return result
  }

  private companion object {
    const val TAG = "MorningBriefWorker"
  }
}
