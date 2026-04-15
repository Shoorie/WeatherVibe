package com.weather.vibe.feature.widget.di

import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.weather.vibe.feature.widget.work.RefreshCurrentLocationWidget
import com.weather.vibe.feature.widget.work.WidgetRefreshWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

// Manual registration — koin-annotations generates a @Worker
// binding that KoinWorkerFactory cannot resolve by class-name
// qualifier. Revisit once upstream is fixed.
val widgetWorkerKoinModule = module {

  single { WorkManager.getInstance(androidContext()) }

  factory<ListenableWorker>(
    qualifier = qualifier(WidgetRefreshWorker::class.java.name)
  ) { params ->
    WidgetRefreshWorker(
      context = androidContext(),
      params = params.get<WorkerParameters>(),
      refreshWidget = get<RefreshCurrentLocationWidget>()
    )
  }
}
