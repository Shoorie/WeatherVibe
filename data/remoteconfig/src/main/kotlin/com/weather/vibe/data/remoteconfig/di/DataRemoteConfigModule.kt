package com.weather.vibe.data.remoteconfig.di

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.remoteconfig")
class DataRemoteConfigModule {

  @Single
  fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig =
    Firebase.remoteConfig
}
