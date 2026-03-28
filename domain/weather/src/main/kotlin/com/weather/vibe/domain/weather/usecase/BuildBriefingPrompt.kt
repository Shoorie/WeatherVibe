package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class BuildBriefingPrompt {

  operator fun invoke(weatherData: WeatherData): String {
    val today = weatherData.dailyForecast.firstOrNull()
    return PROMPT.format(
      weatherData.cityName,
      weatherData.currentTemperature.roundToInt(),
      weatherData.apparentTemperature.roundToInt(),
      weatherData.condition.label,
      today?.minTemperature?.roundToInt() ?: 0,
      today?.maxTemperature?.roundToInt() ?: 0,
      today?.precipitationProbability ?: 0,
      weatherData.windSpeed.roundToInt()
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
