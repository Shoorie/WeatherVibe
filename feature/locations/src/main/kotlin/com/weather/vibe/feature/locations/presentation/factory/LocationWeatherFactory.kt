package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.weather.model.SimplifiedCondition.CLOUDY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.FOGGY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.RAINY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SNOWY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.STORMY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.Cloudy
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.Night
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.PartlyCloudy
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.Rain
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.Snow
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.Sunny
import org.koin.core.annotation.Factory

@Factory
internal class LocationWeatherFactory {

  fun create(snapshot: LocationWeatherSnapshot): LocationWeatherUi {
    if (!snapshot.isDay) return Night
    return when (snapshot.condition) {
      SUNNY -> Sunny
      CLOUDY -> PartlyCloudy
      RAINY -> Rain
      SNOWY -> Snow
      STORMY -> Rain
      FOGGY -> Cloudy
    }
  }
}
