package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class BuildPlaylistPrompt {

  operator fun invoke(excludedGenres: String, weatherData: WeatherData): String {
    val prompt = BASE_PROMPT.format(
      weatherData.cityName,
      weatherData.currentTemperature.roundToInt(),
      weatherData.condition.label,
      weatherData.dailyForecast.firstOrNull()
        ?.precipitationProbability ?: DEFAULT_PRECIPITATION
    )
    return if (excludedGenres.isBlank()) prompt
    else EXCLUDED_GENRES_TEMPLATE.format(prompt, excludedGenres)
  }

  private companion object {
    const val DEFAULT_PRECIPITATION = 0

    const val BASE_PROMPT =
      "Based on this weather in %s: %d°C, %s, %d%% rain chance, " +
        "reply with EXACTLY two lines (no other text):\n" +
        "MOOD: [5–8 words describing the musical mood or vibe of the day]\n" +
        "GENRES: [3 music genres or sub-genres, comma-separated]"

    const val EXCLUDED_GENRES_TEMPLATE = "%s Do not suggest these genres: %s."
  }
}
