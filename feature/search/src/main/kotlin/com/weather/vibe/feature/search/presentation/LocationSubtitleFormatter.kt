package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory

@Factory
internal class LocationSubtitleFormatter {

  operator fun invoke(location: Location): String = buildString {
    if (!location.admin1.isNullOrEmpty()) append(location.admin1)
    if (location.country.isNotEmpty()) {
      if (isNotEmpty()) append(SEPARATOR)
      append(location.country)
    }
  }

  private companion object {
    const val SEPARATOR = ", "
  }
}
