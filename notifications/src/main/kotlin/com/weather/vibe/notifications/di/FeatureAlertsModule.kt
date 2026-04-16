package com.weather.vibe.notifications.di

import android.content.Context
import androidx.work.WorkManager
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.notifications")
class FeatureAlertsModule {

  @Single
  fun workManager(context: Context): WorkManager =
    WorkManager.getInstance(context)
}
