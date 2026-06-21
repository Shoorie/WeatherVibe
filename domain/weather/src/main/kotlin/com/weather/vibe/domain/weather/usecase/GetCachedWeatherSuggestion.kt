package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import java.util.Locale

@Factory
class GetCachedWeatherSuggestion internal constructor(
  private val cache: WeatherSuggestionCache,
  private val observeUserSettings: ObserveUserSettings
) {

  suspend operator fun invoke(
    todayDispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): WeatherSuggestion? {
    val settings = observeUserSettings().first().getOrNull()
    val tone = settings?.briefTone ?: WITTY_AND_FRIENDLY
    val excludedGenres = settings?.excludedGenres.orEmpty()

    return cache.get(
      dispositionEntries = todayDispositionEntries,
      languageTag = Locale.getDefault().language,
      locationId = weatherData.coordinates.id,
      tone = tone,
      weatherKey = weatherKey
    )
      ?.takeIf { it.isValid(excludedGenres) }
      ?.suggestion
  }
}
