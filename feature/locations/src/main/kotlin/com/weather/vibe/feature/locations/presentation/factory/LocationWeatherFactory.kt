package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.weather.model.SimplifiedCondition.CLOUDY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.FOGGY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.RAINY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SNOWY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.STORMY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import org.koin.core.annotation.Factory

@Factory
internal class LocationWeatherFactory {

  fun create(snapshot: LocationWeatherSnapshot): LocationWeatherUi {
    if (!snapshot.isDay) return LocationWeatherUi.Night
    return when (snapshot.condition) {
      SUNNY -> LocationWeatherUi.Sunny
      CLOUDY -> LocationWeatherUi.PartlyCloudy
      RAINY -> LocationWeatherUi.Rain
      SNOWY -> LocationWeatherUi.Snow
      STORMY -> LocationWeatherUi.Rain
      FOGGY -> LocationWeatherUi.Cloudy
    }
  }
}
