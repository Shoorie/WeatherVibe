package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.Persona
import com.weather.vibe.domain.settings.model.Persona.FORMAL
import com.weather.vibe.domain.settings.model.Persona.SARCASTIC
import com.weather.vibe.domain.settings.model.Persona.WITTY
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class BuildBriefingPrompt {

  operator fun invoke(persona: Persona, weatherData: WeatherData): String {
    val today = weatherData.dailyForecast.firstOrNull()
    return PROMPT.format(
      PERSONA_STYLES.getValue(persona),
      weatherData.cityName,
      weatherData.currentTemperature.roundToInt(),
      weatherData.apparentTemperature.roundToInt(),
      weatherData.condition.label,
      today?.minTemperature?.roundToInt() ?: DEFAULT_TEMPERATURE,
      today?.maxTemperature?.roundToInt() ?: DEFAULT_TEMPERATURE,
      today?.precipitationProbability ?: DEFAULT_PRECIPITATION,
      weatherData.windSpeed.roundToInt()
    )
  }

  private companion object {

    const val DEFAULT_PRECIPITATION = 0
    const val DEFAULT_TEMPERATURE = 0

    const val PROMPT =
      "You're a %s weather guide. " +
        "Write a short daily briefing for %s in 1-2 sentences. " +
        "Right now: %d°C (feels like %d°C), %s. " +
        "Today: %d–%d°C, %d%% rain chance, wind %d km/h. "

    val PERSONA_STYLES = mapOf(
      WITTY to "witty and friendly",
      FORMAL to "formal and professional",
      SARCASTIC to "sarcastic and edgy"
    )
  }
}
