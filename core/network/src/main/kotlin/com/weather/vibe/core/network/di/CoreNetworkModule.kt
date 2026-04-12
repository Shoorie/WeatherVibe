package com.weather.vibe.core.network.di

import com.weather.vibe.core.network.createHttpClient
import io.ktor.client.HttpClient
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class CoreNetworkModule {

  @Single
  fun provideHttpClient(): HttpClient =
    createHttpClient()
}
