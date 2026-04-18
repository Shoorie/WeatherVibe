package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.domain.weather.usecase.BuildDailyTemperatureRanges
import com.weather.vibe.domain.weather.usecase.FindCurrentHourIndex
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherMetrics
import com.weather.vibe.domain.weather.usecase.ResolveTodaySunInfo
import com.weather.vibe.domain.weather.usecase.ResolveTodayTemperatureBounds
import org.koin.core.annotation.Factory

@Factory
internal data class HomeFactoryUseCases(
  val buildDailyTemperatureRanges: BuildDailyTemperatureRanges,
  val findCurrentHourIndex: FindCurrentHourIndex,
  val getCurrentWeatherMetrics: GetCurrentWeatherMetrics,
  val resolveTodaySunInfo: ResolveTodaySunInfo,
  val resolveTodayTemperatureBounds: ResolveTodayTemperatureBounds
)
