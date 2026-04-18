package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.core.coroutines.suspendRunCatching
import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.vibe.model.VibeMood
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory

@Factory
class CalculateDailyVibe internal constructor(
  private val scoreAqiBurden: ScoreAqiBurden,
  private val scorePollenBurden: ScorePollenBurden,
  private val scoreRainOutlook: ScoreRainOutlook,
  private val scoreTemperatureComfort: ScoreTemperatureComfort,
  private val scoreUvBurden: ScoreUvBurden,
  private val scoreWindComfort: ScoreWindComfort
) {

  operator fun invoke(
    weather: WeatherData,
    readings: EnvironmentalReadings
  ): Result<DailyVibe> = suspendRunCatching {
    val todayUvIndex = weather.dailyForecast.firstOrNull()?.uvIndexMax ?: 0.0
    val totalPenalty = listOf(
      scoreTemperatureComfort(weather.apparentTemperature),
      scoreRainOutlook(weather.hourlyForecast),
      scoreWindComfort(weather.windSpeed),
      scoreAqiBurden(readings.airQuality?.level),
      scorePollenBurden(readings.pollen),
      scoreUvBurden(todayUvIndex)
    ).sum()
    val score = (BASE_SCORE - totalPenalty).coerceIn(MIN_SCORE, MAX_SCORE)
    DailyVibe(score = score, mood = VibeMood.from(score))
  }

  private companion object {
    const val BASE_SCORE = 100
    const val MIN_SCORE = 0
    const val MAX_SCORE = 100
  }
}
