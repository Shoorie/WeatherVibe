package com.weather.vibe

import android.app.Application
import com.weather.vibe.core.network.di.CoreNetworkModule
import com.weather.vibe.data.location.di.DataLocationModule
import com.weather.vibe.data.weather.di.DataWeatherModule
import com.weather.vibe.domain.location.di.DomainLocationModule
import com.weather.vibe.domain.weather.di.DomainWeatherModule
import com.weather.vibe.feature.home.di.FeatureHomeModule
import com.weather.vibe.feature.search.di.FeatureSearchModule
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
        CoreNetworkModule().module,
        DomainLocationModule().module,
        DomainWeatherModule().module,
        DataLocationModule().module,
        DataWeatherModule().module,
        FeatureHomeModule().module,
        FeatureSearchModule().module
      )
    }
  }
}
