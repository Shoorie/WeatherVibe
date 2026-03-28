package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.cache.MoodPlaylistCache
import com.weather.vibe.domain.weather.model.MoodPlaylist
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.BriefingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import java.time.LocalDate
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.math.roundToInt

@Factory
class GenerateMoodPlaylist(
  private val cache: MoodPlaylistCache,
  private val repository: BriefingRepository
) {

  operator fun invoke(weatherData: WeatherData): Flow<Result<MoodPlaylist>> =
    flow {
      val date = LocalDate.now()
      val cached = cache.getPlaylist(cityName = weatherData.cityName, date = date)
      if (cached != null) {
        emit(success(cached))
        return@flow
      }
      val response = repository.generateBriefing(buildPrompt(weatherData))
      val playlist = parseResponse(response)
      cache.save(cityName = weatherData.cityName, date = date, playlist = playlist)
      emit(success(playlist))
    }.catch { emit(failure(it)) }

  private fun buildPrompt(weather: WeatherData): String =
    PROMPT.format(
      weather.cityName,
      weather.currentTemperature.roundToInt(),
      weather.condition.label,
      weather.dailyForecast.firstOrNull()?.precipitationProbability ?: 0
    )

  private fun parseResponse(response: String): MoodPlaylist {

    val lines = response.trim().lines()

    val mood = lines.firstOrNull { it.startsWith(MOOD_PREFIX) }
      ?.removePrefix(MOOD_PREFIX)
      ?.trim()
      .orEmpty()

    val genres = lines.firstOrNull { it.startsWith(GENRES_PREFIX) }
      ?.removePrefix(GENRES_PREFIX)
      ?.split(",")
      ?.map { it.trim() }
      ?.filter { it.isNotBlank() }
      .orEmpty()

    return MoodPlaylist(genres = genres, mood = mood)
  }

  private companion object {
    const val GENRES_PREFIX = "GENRES:"
    const val MOOD_PREFIX = "MOOD:"
    const val PROMPT =
      "Based on this weather in %s: %d°C, %s, %d%% rain chance, " +
        "reply with EXACTLY two lines (no other text):\n" +
        "MOOD: [5–8 words describing the musical mood or vibe of the day]\n" +
        "GENRES: [3 music genres or sub-genres, comma-separated]"
  }
}
