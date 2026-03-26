package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.cache.BriefingCache
import com.weather.vibe.domain.weather.repository.BriefingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import java.time.LocalDate
import kotlin.math.roundToInt

@Factory
class GenerateDailyBriefing(
  private val cache: BriefingCache,
  private val repository: BriefingRepository
) {

  operator fun invoke(weatherData: WeatherData): Flow<Result<String>> =
    flow {
      val date = LocalDate.now()
      val cached = cache.get(weatherData.cityName, date)
      if (cached != null) {
        emit(Result.success(cached))
        return@flow
      }
      val briefing = repository.generateBriefing(buildPrompt(weatherData))
      cache.save(weatherData.cityName, date, briefing)
      emit(Result.success(briefing))
    }.catch { emit(Result.failure(it)) }

  private fun buildPrompt(weather: WeatherData): String {
    val today = weather.dailyForecast.firstOrNull()
    return PROMPT.format(
      weather.cityName,
      weather.currentTemperature.roundToInt(),
      weather.apparentTemperature.roundToInt(),
      weather.condition.label,
      today?.minTemperature?.roundToInt() ?: 0,
      today?.maxTemperature?.roundToInt() ?: 0,
      today?.precipitationProbability ?: 0,
      weather.windSpeed.roundToInt()
    )
  }

  private companion object {
    const val PROMPT =
      "You're a witty, friendly weather guide. " +
        "Write a daily briefing for %s in 1-2 sentences. " +
        "Right now: %d°C (feels like %d°C), %s. " +
        "Today: %d–%d°C, %d%% rain chance, wind %d km/h. " +
        "Be practical and a little funny. No emojis. Speak directly to the user."
  }
}
