package com.weather.vibe.di

import com.weather.vibe.core.network.createHttpClient
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AppModule {

  @Single
  fun provideHttpClient(): HttpClient = createHttpClient()
}
