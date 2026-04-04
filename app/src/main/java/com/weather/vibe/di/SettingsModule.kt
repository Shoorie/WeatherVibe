package com.weather.vibe.di

import com.weather.vibe.data.settings.di.DataSettingsModule
import com.weather.vibe.domain.settings.di.DomainSettingsModule
import com.weather.vibe.feature.settings.di.FeatureSettingsModule
import org.koin.core.annotation.Module

@Module(
  includes = [
    FeatureSettingsModule::class,
    DomainSettingsModule::class,
    DataSettingsModule::class
  ]
)
class SettingsModule
