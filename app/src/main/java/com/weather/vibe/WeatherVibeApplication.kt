package com.weather.vibe

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.weather.vibe.core.tracing.TraceSections.KOIN_INITIALIZATION
import com.weather.vibe.core.tracing.traceSection
import com.weather.vibe.data.ads.initializer.AdMobInitializer
import com.weather.vibe.data.remoteconfig.initializer.RemoteConfigInitializer
import com.weather.vibe.di.WeatherVibeApp
import com.weather.vibe.feature.widget.work.WidgetRefreshCoordinator
import com.weather.vibe.scheduling.SchedulingCoordinator
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
    get<RemoteConfigInitializer>().start()
    get<AdMobInitializer>().start()
    get<WidgetRefreshCoordinator>().start()
    get<SchedulingCoordinator>().start()
  }
}
