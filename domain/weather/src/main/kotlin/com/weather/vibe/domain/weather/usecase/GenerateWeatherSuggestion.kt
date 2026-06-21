package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.premium.usecase.CanGenerateBrief
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherBriefResult
import com.weather.vibe.domain.weather.model.WeatherBriefResult.LimitReached
import com.weather.vibe.domain.weather.model.WeatherBriefResult.Ready
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GenerateWeatherSuggestion internal constructor(
  private val canGenerateBrief: CanGenerateBrief,
  private val fetchWeatherSuggestion: FetchWeatherSuggestion,
  private val generationLock: Mutex,
  private val getCachedWeatherSuggestion: GetCachedWeatherSuggestion
) {

  operator fun invoke(
    todayDispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): Flow<Result<WeatherBriefResult>> =
    flow {
      val brief = resolveBrief(todayDispositionEntries, weatherData, weatherKey)
      emit(success(brief))
    }.catch { emit(failure(it)) }

  private suspend fun resolveBrief(
    dispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): WeatherBriefResult {
    cachedSuggestion(dispositionEntries, weatherData, weatherKey)?.let { return Ready(it) }
    return generateBrief(dispositionEntries, weatherData, weatherKey)
  }

  private suspend fun cachedSuggestion(
    dispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): WeatherSuggestion? =
    getCachedWeatherSuggestion(
      todayDispositionEntries = dispositionEntries,
      weatherData = weatherData,
      weatherKey = weatherKey
    )

  private suspend fun generateBrief(
    dispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): WeatherBriefResult =
    generationLock.withLock {
      val cached = cachedSuggestion(dispositionEntries, weatherData, weatherKey)
      when {
        cached != null -> Ready(cached)
        !canGenerateBrief() -> LimitReached
        else -> Ready(fetchBrief(dispositionEntries, weatherData, weatherKey))
      }
    }

  private suspend fun fetchBrief(
    dispositionEntries: List<UserDispositionEntry>,
    weatherData: WeatherData,
    weatherKey: WeatherKey
  ): WeatherSuggestion =
    fetchWeatherSuggestion(
      todayDispositionEntries = dispositionEntries,
      weatherData = weatherData,
      weatherKey = weatherKey
    )
}
