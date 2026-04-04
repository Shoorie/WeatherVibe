package com.weather.vibe.di

import com.weather.vibe.data.location.di.DataLocationModule
import com.weather.vibe.domain.location.di.DomainLocationModule
import com.weather.vibe.feature.search.di.FeatureSearchModule
import org.koin.core.annotation.Module

@Module(
  includes = [
    FeatureSearchModule::class,
    DomainLocationModule::class,
    DataLocationModule::class
  ]
)
class SearchModule
