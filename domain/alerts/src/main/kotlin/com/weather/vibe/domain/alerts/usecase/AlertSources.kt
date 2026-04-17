package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.airquality.usecase.GetPollen
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.weather.usecase.GetWeather
import org.koin.core.annotation.Factory

@Factory
internal data class AlertSources(
  val getAirQuality: GetAirQuality,
  val getPollen: GetPollen,
  val getWeather: GetWeather,
  val observeCurrentLocation: ObserveCurrentLocation
)
