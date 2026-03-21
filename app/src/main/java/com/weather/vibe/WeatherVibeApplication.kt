package com.weather.vibe

import android.app.Application
import com.weather.vibe.data.weather.di.DataWeatherModule
import com.weather.vibe.di.AppModule
import com.weather.vibe.domain.weather.di.DomainWeatherModule
import com.weather.vibe.feature.home.di.FeatureHomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

class WeatherVibeApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@WeatherVibeApplication)
      modules(
        AppModule().module,
        DomainWeatherModule().module,
        DataWeatherModule().module,
        FeatureHomeModule().module
      )
    }
  }
}
