package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.core.coroutines.suspendRunCatching
import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.model.Pollen
import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.airquality.usecase.GetPollen
import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.vibe.model.VibeMood
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import org.koin.core.annotation.Factory

@Factory
class CalculateDailyVibe internal constructor(
  private val getAirQuality: GetAirQuality,
  private val getPollen: GetPollen,
  private val scoreAqiBurden: ScoreAqiBurden,
  private val scorePollenBurden: ScorePollenBurden,
  private val scoreRainOutlook: ScoreRainOutlook,
  private val scoreTemperatureComfort: ScoreTemperatureComfort,
  private val scoreWindComfort: ScoreWindComfort
) {

  suspend operator fun invoke(weather: WeatherData): Result<DailyVibe> =
    suspendRunCatching {
      val (airQuality, pollen) = fetchEnvironmentReadings(weather.coordinates)
      val totalPenalty = listOf(
        scoreTemperatureComfort(weather.apparentTemperature),
        scoreRainOutlook(weather.hourlyForecast),
        scoreWindComfort(weather.windSpeed),
        scoreAqiBurden(airQuality?.level),
        scorePollenBurden(pollen)
      ).sum()
      val score = (BASE_SCORE - totalPenalty).coerceIn(MIN_SCORE, MAX_SCORE)
      DailyVibe(score = score, mood = VibeMood.from(score))
    }

  private suspend fun fetchEnvironmentReadings(
    coordinates: Coordinates
  ): Pair<AirQuality?, Pollen?> = supervisorScope {
    val airQuality = async { getAirQuality(coordinates) }
    val pollen = async { getPollen(coordinates) }
    airQuality.await().getOrNull() to pollen.await().getOrNull()
  }

  private companion object {
    const val BASE_SCORE = 100
    const val MIN_SCORE = 0
    const val MAX_SCORE = 100
  }
}
