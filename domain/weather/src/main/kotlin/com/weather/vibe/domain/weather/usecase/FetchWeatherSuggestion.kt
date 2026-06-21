package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.usecase.AddToGenreHistory
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.repository.WeatherSuggestionRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import java.util.Locale

@Factory
class FetchWeatherSuggestion internal constructor(
  private val addToGenreHistory: AddToGenreHistory,
  private val buildWeatherSuggestionPrompt: BuildWeatherSuggestionPrompt,
  private val cache: WeatherSuggestionCache,
  private val observeUserSettings: ObserveUserSettings,
  private val repository: WeatherSuggestionRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(
    todayDispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): WeatherSuggestion {
    val settings = observeUserSettings().first().getOrNull()
    val tone = settings?.briefTone ?: WITTY_AND_FRIENDLY
    val excludedGenres = settings?.excludedGenres.orEmpty()
    val languageTag = Locale.getDefault().language
    val locationId = weatherData.coordinates.id

    val prompt = buildWeatherSuggestionPrompt(
      condition = weatherKey.condition,
      currentDate = timeProvider.today(),
      excludedGenres = excludedGenres,
      locationName = weatherData.coordinates.name,
      temperatureCelsius = weatherData.currentTemperature,
      timeOfDay = weatherKey.timeOfDay,
      todayDispositionEntries = todayDispositionEntries,
      tone = tone
    )
    val suggestion = repository.getSuggestionBasedOn(prompt)
    cache.save(
      dispositionEntries = todayDispositionEntries,
      languageTag = languageTag,
      locationId = locationId,
      suggestion = suggestion,
      tone = tone,
      weatherKey = weatherKey
    )
    addToGenreHistory(genres = suggestion.genres.toSet())
    return suggestion
  }
}
