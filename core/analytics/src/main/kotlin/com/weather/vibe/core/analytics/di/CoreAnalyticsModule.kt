package com.weather.vibe.core.analytics.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.core.analytics")
class CoreAnalyticsModule {

  @Single
  fun provideFirebaseAnalytics(): FirebaseAnalytics =
    Firebase.analytics
}
