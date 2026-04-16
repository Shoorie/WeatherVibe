package com.weather.vibe.notifications.di

import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.weather.vibe.notifications.work.CheckWeatherAlerts
import com.weather.vibe.notifications.work.DeliverMorningBrief
import com.weather.vibe.notifications.work.MorningBriefWorker
import com.weather.vibe.notifications.work.WeatherAlertsWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

// Manual registration — koin-annotations generates a @Worker
// binding that KoinWorkerFactory cannot resolve by class-name
// qualifier. Revisit once upstream is fixed.
val weatherAlertsWorkerKoinModule = module {

  single { WorkManager.getInstance(androidContext()) }

  factory<ListenableWorker>(
    qualifier = qualifier(WeatherAlertsWorker::class.java.name)
  ) { params ->
    WeatherAlertsWorker(
      context = androidContext(),
      params = params.get<WorkerParameters>(),
      checkWeatherAlerts = get<CheckWeatherAlerts>()
    )
  }

  factory<ListenableWorker>(
    qualifier = qualifier(MorningBriefWorker::class.java.name)
  ) { params ->
    MorningBriefWorker(
      context = androidContext(),
      params = params.get<WorkerParameters>(),
      deliverMorningBrief = get<DeliverMorningBrief>()
    )
  }
}
