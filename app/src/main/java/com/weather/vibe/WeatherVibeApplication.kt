package com.weather.vibe

import android.app.Application
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.work.Configuration
import com.weather.vibe.core.tracing.TraceSections.KOIN_INITIALIZATION
import com.weather.vibe.core.tracing.traceSection
import com.weather.vibe.dev.LocationFavoritesSeeder
import com.weather.vibe.di.WeatherVibeApp
import com.weather.vibe.feature.widget.work.WidgetRefreshCoordinator
import com.weather.vibe.scheduling.SchedulingCoordinator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.logger.Level
import org.koin.ksp.generated.startKoin

class WeatherVibeApplication : Application(), Configuration.Provider {

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
    get<WidgetRefreshCoordinator>().start()
    get<SchedulingCoordinator>().start()
    if (isDebuggable) seedDummyFavoritesIfEmpty()
  }

  private val isDebuggable: Boolean
    get() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

  private fun seedDummyFavoritesIfEmpty() {
    val seeder = get<LocationFavoritesSeeder>()
    val handler = CoroutineExceptionHandler { _, throwable ->
      Log.w(TAG_SEEDER, "Dummy favorites seed failed", throwable)
    }
    CoroutineScope(SupervisorJob() + IO + handler).launch { seeder.seedIfEmpty() }
  }

  private companion object {
    const val TAG_SEEDER = "LocationFavoritesSeeder"
  }
}
