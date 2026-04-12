package com.weather.vibe.domain.weather.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@Configuration
@ComponentScan("com.weather.vibe.domain.weather")
class DomainWeatherModule
