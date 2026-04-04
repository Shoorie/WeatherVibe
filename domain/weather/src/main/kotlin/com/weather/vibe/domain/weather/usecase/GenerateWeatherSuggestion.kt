package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.usecase.AddToGenreHistory
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.repository.WeatherSuggestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import java.util.Locale
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GenerateWeatherSuggestion internal constructor(
  private val addToGenreHistory: AddToGenreHistory,
  private val buildWeatherSuggestionPrompt: BuildWeatherSuggestionPrompt,
  private val cache: WeatherSuggestionCache,
  private val observeUserSettings: ObserveUserSettings,
  private val repository: WeatherSuggestionRepository
) {

  operator fun invoke(
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): Flow<Result<WeatherSuggestion>> =
    flow {

      val settings = observeUserSettings().first().getOrNull()
      val tone = settings?.briefTone ?: WITTY_AND_FRIENDLY
      val excludedGenres = settings?.excludedGenres.orEmpty()
      val languageTag = Locale.getDefault().language

      val suggestion = cachedSuggestion(languageTag, tone, weatherKey, excludedGenres)
        ?: fetchSuggestion(languageTag, weatherData, weatherKey, tone, excludedGenres)

      emit(success(suggestion))

    }.catch { emit(failure(it)) }

  private suspend fun cachedSuggestion(
    languageTag: String,
    tone: BriefTone,
    weatherKey: WeatherKey,
    excludedGenres: Set<String>
  ): WeatherSuggestion? =
    cache.get(languageTag, tone, weatherKey)
      ?.takeIf { it.isValid(excludedGenres) }
      ?.suggestion

  private suspend fun fetchSuggestion(
    languageTag: String,
    weatherData: WeatherData,
    weatherKey: WeatherKey,
    tone: BriefTone,
    excludedGenres: Set<String>
  ): WeatherSuggestion {
    val prompt = buildWeatherSuggestionPrompt(
      condition = weatherKey.condition,
      excludedGenres = excludedGenres,
      languageTag = languageTag,
      temperatureCelsius = weatherData.currentTemperature,
      timeOfDay = weatherKey.timeOfDay,
      tone = tone
    )
    val suggestion = repository.generate(prompt = prompt)
    cache.save(
      languageTag = languageTag,
      suggestion = suggestion,
      tone = tone,
      weatherKey = weatherKey
    )
    addToGenreHistory(genres = suggestion.genres.toSet())
    return suggestion
  }
}
