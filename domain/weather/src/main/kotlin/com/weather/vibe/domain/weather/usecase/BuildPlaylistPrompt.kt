package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class BuildPlaylistPrompt {

  operator fun invoke(weatherData: WeatherData): String =
    PROMPT.format(
      weatherData.cityName,
      weatherData.currentTemperature.roundToInt(),
      weatherData.condition.label,
      weatherData.dailyForecast.firstOrNull()?.precipitationProbability ?: 0
    )

  private companion object {
    const val PROMPT =
      "Based on this weather in %s: %d°C, %s, %d%% rain chance, " +
        "reply with EXACTLY two lines (no other text):\n" +
        "MOOD: [5–8 words describing the musical mood or vibe of the day]\n" +
        "GENRES: [3 music genres or sub-genres, comma-separated]"
  }
}
