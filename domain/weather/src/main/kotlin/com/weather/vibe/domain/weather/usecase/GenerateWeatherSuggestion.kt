package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.usecase.AddToGenreHistory
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.model.WeatherSuggestionPromptInput
import com.weather.vibe.domain.weather.repository.WeatherSuggestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Factory
import java.util.Locale
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GenerateWeatherSuggestion internal constructor(
  private val addToGenreHistory: AddToGenreHistory,
  private val buildWeatherSuggestionPrompt: BuildWeatherSuggestionPrompt,
  private val cache: WeatherSuggestionCache,
  private val generationLock: Mutex,
  private val observeUserSettings: ObserveUserSettings,
  private val repository: WeatherSuggestionRepository,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(
    todayDispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): Flow<Result<WeatherSuggestion>> =
    flow {

      val settings = observeUserSettings().first().getOrNull()
      val tone = settings?.briefTone ?: WITTY_AND_FRIENDLY
      val excludedGenres = settings?.excludedGenres.orEmpty()
      val languageTag = Locale.getDefault().language
      val locationId = weatherData.coordinates.id

      val suggestion = cachedSuggestion(
        languageTag = languageTag,
        locationId = locationId,
        tone = tone,
        weatherKey = weatherKey,
        dispositionEntries = todayDispositionEntries,
        excludedGenres = excludedGenres
      ) ?: generateSuggestion(
        languageTag = languageTag,
        locationId = locationId,
        weatherData = weatherData,
        weatherKey = weatherKey,
        dispositionEntries = todayDispositionEntries,
        tone = tone,
        excludedGenres = excludedGenres
      )

      emit(success(suggestion))
    }.catch { emit(failure(it)) }

  private suspend fun cachedSuggestion(
    languageTag: String,
    locationId: String,
    tone: BriefTone,
    weatherKey: WeatherKey,
    dispositionEntries: List<UserDispositionEntry>,
    excludedGenres: Set<String>
  ): WeatherSuggestion? =
    cache.get(
      dispositionEntries = dispositionEntries,
      languageTag = languageTag,
      locationId = locationId,
      tone = tone,
      weatherKey = weatherKey
    )
      ?.takeIf { it.isValid(excludedGenres) }
      ?.suggestion

  private suspend fun generateSuggestion(
    languageTag: String,
    locationId: String,
    weatherData: WeatherData,
    weatherKey: WeatherKey,
    dispositionEntries: List<UserDispositionEntry>,
    tone: BriefTone,
    excludedGenres: Set<String>
  ): WeatherSuggestion =
    generationLock.withLock {
      cachedSuggestion(
        languageTag = languageTag,
        locationId = locationId,
        tone = tone,
        weatherKey = weatherKey,
        dispositionEntries = dispositionEntries,
        excludedGenres = excludedGenres
      ) ?: fetchSuggestion(
        languageTag = languageTag,
        locationId = locationId,
        weatherData = weatherData,
        weatherKey = weatherKey,
        dispositionEntries = dispositionEntries,
        tone = tone,
        excludedGenres = excludedGenres
      )
    }

  private suspend fun fetchSuggestion(
    languageTag: String,
    locationId: String,
    weatherData: WeatherData,
    weatherKey: WeatherKey,
    dispositionEntries: List<UserDispositionEntry>,
    tone: BriefTone,
    excludedGenres: Set<String>
  ): WeatherSuggestion {
    val prompt = buildWeatherSuggestionPrompt(
      WeatherSuggestionPromptInput(
        condition = weatherKey.condition,
        currentDate = timeProvider.today(),
        excludedGenres = excludedGenres,
        locationName = weatherData.coordinates.name,
        temperatureCelsius = weatherData.currentTemperature,
        timeOfDay = weatherKey.timeOfDay,
        todayDispositionEntries = dispositionEntries,
        tone = tone
      )
    )
    val suggestion = repository.getSuggestionBasedOn(prompt)
    cache.save(
      dispositionEntries = dispositionEntries,
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
