package com.weather.vibe

import android.app.Application
import com.weather.vibe.core.tracing.TraceSections.KOIN_INITIALIZATION
import com.weather.vibe.core.tracing.traceSection
import com.weather.vibe.di.WeatherVibeApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.ksp.generated.startKoin

class WeatherVibeApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    traceSection(KOIN_INITIALIZATION) {
      WeatherVibeApp.startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@WeatherVibeApplication)
      }
    }
  }
}
