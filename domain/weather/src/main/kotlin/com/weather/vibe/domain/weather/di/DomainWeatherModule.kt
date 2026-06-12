package com.weather.vibe.domain.weather.di

import kotlinx.coroutines.sync.Mutex
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.domain.weather")
class DomainWeatherModule {

  @Single
  fun provideSuggestionGenerationLock(): Mutex = Mutex()
}
