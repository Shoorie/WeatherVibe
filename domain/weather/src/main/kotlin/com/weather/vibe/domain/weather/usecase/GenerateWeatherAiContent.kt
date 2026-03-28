package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.cache.WeatherAiCache
import com.weather.vibe.domain.weather.model.WeatherAiContent
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherAiRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import java.time.LocalDate
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GenerateWeatherAiContent internal constructor(
  private val buildBriefingPrompt: BuildBriefingPrompt,
  private val buildPlaylistPrompt: BuildPlaylistPrompt,
  private val cache: WeatherAiCache,
  private val parsePlaylistResponse: ParsePlaylistResponse,
  private val repository: WeatherAiRepository
) {

  operator fun invoke(weatherData: WeatherData): Flow<Result<WeatherAiContent>> =
    flow {

      val date = LocalDate.now()
      val cached = cache.get(cityName = weatherData.cityName, date = date)

      if (cached != null) {
        emit(success(cached))
        return@flow
      }

      val briefingPrompt = buildBriefingPrompt(weatherData)
      val playlistPrompt = buildPlaylistPrompt(weatherData)
      val content = getWeatherContent(briefingPrompt, playlistPrompt)
      cache.save(cityName = weatherData.cityName, content = content, date = date)
      emit(success(content))
    }.catch { emit(failure(it)) }

  private suspend fun getWeatherContent(
    briefingPrompt: String,
    playlistPrompt: String
  ) = coroutineScope {

    val briefing = async { repository.generate(briefingPrompt) }
    val rawPlaylist = async { repository.generate(playlistPrompt) }
    val playlist = parsePlaylistResponse(rawPlaylist.await())

    WeatherAiContent(
      briefing = briefing.await(),
      playlist = playlist
    )
  }
}
