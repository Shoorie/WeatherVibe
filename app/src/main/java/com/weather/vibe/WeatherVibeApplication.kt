package com.weather.vibe

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.weather.vibe.core.tracing.TraceSections.KOIN_INITIALIZATION
import com.weather.vibe.core.tracing.traceSection
import com.weather.vibe.di.WeatherVibeApp
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.feature.widget.work.WidgetRefreshScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.logger.Level
import org.koin.ksp.generated.startKoin

class WeatherVibeApplication : Application(), Configuration.Provider {

  private val getRecentLocations: GetRecentLocations by inject()
  private val widgetRefreshScheduler: WidgetRefreshScheduler by inject()

  private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder()
      .setMinimumLoggingLevel(Log.INFO)
      .setWorkerFactory(KoinWorkerFactory())
      .build()

  override fun onCreate() {
    super.onCreate()
    traceSection(KOIN_INITIALIZATION) {
      WeatherVibeApp.startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@WeatherVibeApplication)
      }
    }
    widgetRefreshScheduler.schedulePeriodic()
    refreshWidgetOnCurrentLocationChange()
  }

  private fun refreshWidgetOnCurrentLocationChange() {
    getRecentLocations()
      .mapNotNull { it.getOrNull()?.firstOrNull()?.id }
      .distinctUntilChanged()
      .onEach { widgetRefreshScheduler.refreshNow() }
      .launchIn(applicationScope)
  }
}
