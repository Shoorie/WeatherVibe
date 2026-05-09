package com.weather.vibe.core.remoteconfig.di

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.core.remoteconfig")
class CoreRemoteConfigModule {

  @Single
  fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig = Firebase.remoteConfig
}
