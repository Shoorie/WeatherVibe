package com.weather.vibe.feature.widget.di

import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.weather.vibe.feature.widget.work.RefreshCurrentLocationWidget
import com.weather.vibe.feature.widget.work.WidgetRefreshWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

/**
 * Manual worker registration — a workaround for koin-annotations 2.3.1, which generates
 * `worker() { ... }` without the className qualifier and reads WorkerParameters from the module
 * scope instead of `parametersOf`. `KoinWorkerFactory` then cannot locate the bean and
 * WorkManager falls back to reflection on a non-existent `(Context, WorkerParameters)`
 * constructor. Kept out of `@Module @ComponentScan` until upstream fixes the generator.
 */
val widgetWorkerKoinModule = module {

  single { WorkManager.getInstance(androidContext()) }

  factory<ListenableWorker>(qualifier = qualifier(WidgetRefreshWorker::class.java.name)) { params ->
    WidgetRefreshWorker(
      context = androidContext(),
      params = params.get<WorkerParameters>(),
      refreshWidget = get<RefreshCurrentLocationWidget>()
    )
  }
}
