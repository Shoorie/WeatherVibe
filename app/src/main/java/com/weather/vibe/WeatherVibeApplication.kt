package com.weather.vibe

import android.app.Application
import com.weather.vibe.core.ai.di.CoreAiModule
import com.weather.vibe.core.network.di.CoreNetworkModule
import com.weather.vibe.data.location.di.DataLocationModule
import com.weather.vibe.data.settings.di.DataSettingsModule
import com.weather.vibe.data.weather.di.DataWeatherModule
import com.weather.vibe.domain.location.di.DomainLocationModule
import com.weather.vibe.domain.settings.di.DomainSettingsModule
import com.weather.vibe.domain.weather.di.DomainWeatherModule
import com.weather.vibe.feature.home.di.FeatureHomeModule
import com.weather.vibe.feature.search.di.FeatureSearchModule
import com.weather.vibe.feature.settings.di.FeatureSettingsModule
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
        CoreAiModule().module,
        CoreNetworkModule().module,
        DomainLocationModule().module,
        DomainSettingsModule().module,
        DomainWeatherModule().module,
        DataLocationModule().module,
        DataSettingsModule().module,
        DataWeatherModule().module,
        FeatureHomeModule().module,
        FeatureSearchModule().module,
        FeatureSettingsModule().module
      )
    }
  }
}
