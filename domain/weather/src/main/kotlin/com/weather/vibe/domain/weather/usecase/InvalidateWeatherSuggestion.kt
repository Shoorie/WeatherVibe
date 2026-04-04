package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.WeatherKey
import org.koin.core.annotation.Factory
import java.util.Locale

@Factory
class InvalidateWeatherSuggestion internal constructor(
  private val cache: WeatherSuggestionCache
) {

  suspend operator fun invoke(tone: BriefTone, weatherKey: WeatherKey) {
    cache.delete(
      languageTag = Locale.getDefault().language,
      tone = tone,
      weatherKey = weatherKey
    )
  }
}
